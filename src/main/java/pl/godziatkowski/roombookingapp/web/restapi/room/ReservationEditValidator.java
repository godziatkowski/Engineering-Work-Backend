package pl.godziatkowski.roombookingapp.web.restapi.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IReservationSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class ReservationEditValidator
    extends AbstractValidator {

    private final IReservationSnapshotFinder reservationSnapshotFinder;
    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public ReservationEditValidator(IReservationSnapshotFinder reservationSnapshotFinder,
        IRoomSnapshotFinder roomSnapshotFinder) {
        this.reservationSnapshotFinder = reservationSnapshotFinder;
        this.roomSnapshotFinder = roomSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ReservationEdit.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        ReservationEdit reservationEdit = (ReservationEdit) target;
        if (reservationSnapshotFinder.findOneById(reservationEdit.getId()) == null){
            errors.rejectValue("id", "RequestedReservationDoesNotExist");
        }

        RoomSnapshot roomSnapshot = roomSnapshotFinder.findOneById(reservationEdit.getRoomId());
        if (roomSnapshot == null) {
            errors.rejectValue("roomId", "RoomDoesNotExist");
        } else if (!roomSnapshot.isUsable()) {
            errors.rejectValue("roomId", "RoomIsNotUsable");
        }
        if (reservationEdit.getEndDate().isBefore(reservationEdit.getStartDate())) {
            errors.rejectValue("endDate", "ReservationEndDateCannotBeBeforeReservationStartDate");
        }
        List<ReservationSnapshot> reservationSnapshots = reservationSnapshotFinder.findAllActiveByRoomIdAndTimePeriod(
            reservationEdit.getRoomId(),
            reservationEdit.getStartDate(),
            reservationEdit.getEndDate());
        if (!reservationSnapshots.isEmpty()) {
            if (reservationSnapshots.size() > 1 || !reservationSnapshots.get(0).getId().equals(reservationEdit.getId())) {
                errors.rejectValue("roomId", "RommAlreadyReservedAtGivenTime");
            }
        }
    }
}
