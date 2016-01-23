package pl.godziatkowski.roombookingapp.domain.room.entity.steps.room;

import java.time.LocalDateTime;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

public class WhenReservationTest
    extends Stage<WhenReservationTest> {

    @ExpectedScenarioState
    private Reservation reservation;

    @ExpectedScenarioState
    private Map<String, Object> reservationData;

    @ProvidedScenarioState
    private boolean exceptionWasThrown;
    @ProvidedScenarioState
    private ReservationSnapshot reservationSnapshot;

    public void edit_invoked() {
        reservation.edit(
            (Room) reservationData.get("room"),
            (LocalDateTime) reservationData.get("startDate"),
            (LocalDateTime) reservationData.get("endDate"));
    }

    public void cancel_reservation_is_invoked() {
        reservation.cancelReservation();
    }

    public void to_snapshot_invoked() {
        exceptionWasThrown = false;
        try {
            reservationSnapshot = reservation.toSnapshot();
        } catch (EntityInStateNewException e) {
            exceptionWasThrown = true;
        }
    }

}
