package pl.godziatkowski.roombookingapp.web.restapi.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IReservationSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class ReservationNewValidator
    extends AbstractValidator {

    private final IReservationSnapshotFinder reservationSnapshotFinder;
    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public ReservationNewValidator(IReservationSnapshotFinder reservationSnapshotFinder,
        IRoomSnapshotFinder roomSnapshotFinder) {
        this.reservationSnapshotFinder = reservationSnapshotFinder;
        this.roomSnapshotFinder = roomSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ReservationNew.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        ReservationNew reservationNew = (ReservationNew) target;
        RoomSnapshot roomSnapshot = roomSnapshotFinder.findOneById(reservationNew.getRoomId());
        if (roomSnapshot == null) {
            errors.rejectValue("roomId", "RoomDoesNotExist");
        } else if (!roomSnapshot.isUsable()) {
            errors.rejectValue("roomId", "RoomIsNotUsable");
        }
        if (reservationNew.getEndDate().isBefore(reservationNew.getStartDate())) {
            errors.rejectValue("endDate", "ReservationEndDateCannotBeBeforeReservationStartDate");
        }
        if (!reservationSnapshotFinder.findAllActiveByRoomIdAndTimePeriod(reservationNew.getRoomId(),
            reservationNew.getStartDate(),
            reservationNew.getEndDate()).isEmpty()) {
            errors.rejectValue("roomId", "RommAlreadyReservedAtGivenTime");
        }
    }
}
