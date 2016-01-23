package pl.godziatkowski.roombookingapp.web.restapi.room;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.godziatkowski.roombookingapp.domain.room.bo.IReservationBO;
import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IReservationSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/reservation")
public class ReservationApi {

    private final IReservationBO reservationBO;
    private final IReservationSnapshotFinder reservationSnapshotFinder;
    private final IUserSnapshotFinder userSnapshotFinder;
    private final IRoomSnapshotFinder roomSnapshotFinder;
    private final Validator reservationNewValidator;
    private final Validator reservationEditValidator;

    @Autowired
    public ReservationApi(IReservationBO reservationBO, IReservationSnapshotFinder reservationSnapshotFinder,
        IUserSnapshotFinder userSnapshotFinder, IRoomSnapshotFinder roomSnapshotFinder,
        @Qualifier("reservationNewValidator") Validator reservationNewValidator,
        @Qualifier("reservationEditValidator") Validator reservationEditValidator) {
        this.reservationBO = reservationBO;
        this.reservationSnapshotFinder = reservationSnapshotFinder;
        this.userSnapshotFinder = userSnapshotFinder;
        this.roomSnapshotFinder = roomSnapshotFinder;
        this.reservationNewValidator = reservationNewValidator;
        this.reservationEditValidator = reservationEditValidator;
    }

    @InitBinder("reservationNew")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(reservationNewValidator);
    }

    @InitBinder("reservationEdit")
    protected void initEditBinder(WebDataBinder binder) {
        binder.setValidator(reservationEditValidator);
    }

    private UserSnapshot getLoggedUserSnapshot() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        return userSnapshotFinder.findOneByLoginIgnoreCase(login);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myReservations")
    public HttpEntity<List<Reservation>> myReservations(ReservationSearchParams reservationSearchParams) {
        UserSnapshot userSnapshot = getLoggedUserSnapshot();
        List<ReservationSnapshot> reservationSnapshots;
        if (onlyOneDateIsGiven(reservationSearchParams)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (reservationSearchParams.getFromDate() == null || reservationSearchParams.getToDate() == null) {
            reservationSnapshots = reservationSnapshotFinder.findAllByUserId(userSnapshot.getId());
        } else {
            reservationSnapshots = reservationSnapshotFinder.findAllByUserIdAndStartDateBetweenAndEndDateBetween(
                userSnapshot.getId(), reservationSearchParams.getFromDate(), reservationSearchParams.getToDate());
        }

        List<Reservation> reservations = reservationSnapshots.stream().map(reservationSnapshot -> {
            return new Reservation(reservationSnapshot, userSnapshot);
        }).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(reservations);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/room")
    public HttpEntity<List<Reservation>> reservationsForRoom(ReservationSearchParams reservationSearchParams) {
        if (reservationSearchParams.getRoomId() == null && onlyOneDateIsGiven(reservationSearchParams)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }       
        List<ReservationSnapshot> reservationsSnapshots;
        if (reservationSearchParams.getFromDate() == null && reservationSearchParams.getToDate() == null) {
            reservationsSnapshots = reservationSnapshotFinder.findAllByRoomIdAndActive(
                reservationSearchParams.getRoomId());
        } else {
            reservationsSnapshots = reservationSnapshotFinder.findAllActiveByRoomIdAndTimePeriod(
                reservationSearchParams.getRoomId(), reservationSearchParams.getFromDate(),
                reservationSearchParams.getToDate());
        }
        Set<Long> userIds = reservationsSnapshots.stream()
            .map(ReservationSnapshot::getUserId)
            .collect(Collectors.toSet());

        Map<Long, UserSnapshot> userSnapshots = userSnapshotFinder.findAsMapByUserIdIn(userIds);

        List<Reservation> reservations = reservationsSnapshots.stream()
            .map(reservationsSnapshot -> {
                return new Reservation(reservationsSnapshot, userSnapshots.get(reservationsSnapshot.getUserId()));
            })
            .collect(Collectors.toList());      

        return ResponseEntity
            .ok()
            .body(reservations);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/building/{buildingId}")
    public HttpEntity<Map<Long, List<Reservation>>> reservationsForRooms(@PathVariable("buildingId") Long buildingId) {
        List<ReservationSnapshot> reservationSnapshots = reservationSnapshotFinder.findAllActiveByBuildingId(buildingId);
        List<Reservation> reservations = reservationSnapshots.stream().map(reservationSnapshot -> {
            return new Reservation(reservationSnapshot);
        }).collect(Collectors.toList());
        Map<Long, List<Reservation>> reservationsGroupedByRoomId = reservations.stream()
            .collect(
                Collectors.groupingBy(
                    reservation -> {
                        return reservation.getRoomId();
                    },
                    Collectors.toList()
                )
            );
        return ResponseEntity
            .ok()
            .body(reservationsGroupedByRoomId);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Reservation> reserve(@Valid @RequestBody ReservationNew reservationNew) {
        UserSnapshot userSnapshot = getLoggedUserSnapshot();
        ReservationSnapshot reservationSnapshot
            = reservationBO.reserveRoom(reservationNew.getRoomId(), userSnapshot.getId(),
                reservationNew.getStartDate(), reservationNew.getEndDate());
        return ResponseEntity
            .ok()
            .body(new Reservation(reservationSnapshot, userSnapshot));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Reservation> edit(@Valid @RequestBody ReservationEdit reservationEdit) {
        ReservationSnapshot reservationSnapshot = reservationSnapshotFinder.findOneById(reservationEdit.getId());
        if (reservationSnapshot == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserSnapshot userSnapshot = getLoggedUserSnapshot();
        if (!reservationSnapshot.getUserId().equals(userSnapshot.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        reservationBO.edit(reservationEdit.getId(), reservationEdit.getRoomId(),
            reservationEdit.getStartDate(), reservationEdit.getEndDate());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/cancel")
    public HttpEntity<Reservation> cancel(@PathVariable("id") Long id) {
        reservationBO.cancel(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean onlyOneDateIsGiven(ReservationSearchParams reservationSearchParams) {
        return reservationSearchParams.getFromDate() == null && reservationSearchParams.getToDate() != null
            || reservationSearchParams.getFromDate() != null && reservationSearchParams.getToDate() == null;
    }

}
