package pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation;

import java.time.LocalDateTime;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.bo.IReservationBO;
import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyReservedAtGivenDateAndTimeException;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomIsNotUsableException;
import pl.godziatkowski.roombookingapp.domain.room.exception.TimeOfReservationStartAfterEndTimeException;

public class WhenReservationBOTest
    extends Stage<WhenReservationBOTest> {

    @ExpectedScenarioState
    private IReservationBO reservationBO;
    @ExpectedScenarioState
    private Map<String, Object> reservationData;
    @ProvidedScenarioState
    private ReservationSnapshot reservationSnapshot;
    @ProvidedScenarioState
    private Boolean exceptionWasThrown;
    @ProvidedScenarioState
    private RuntimeException catchedException;

    public void reserve_invoked() {
        exceptionWasThrown = false;
        catchedException = null;
        try {
            reservationSnapshot = reservationBO.reserveRoom(
                (Long) reservationData.get("roomId"),
                (Long) reservationData.get("userId"),
                (LocalDateTime) reservationData.get("startDate"),
                (LocalDateTime) reservationData.get("endDate")
            );
        } catch (RoomIsNotUsableException | RoomAlreadyReservedAtGivenDateAndTimeException | TimeOfReservationStartAfterEndTimeException e) {
            exceptionWasThrown = true;
            catchedException = e;
        }
    }

    public void edit_invoked() {
        exceptionWasThrown = false;
        catchedException = null;
        try {
            reservationBO.edit(
                (Long) reservationData.get("reservationId"),
                (Long) reservationData.get("roomId"),
                (LocalDateTime) reservationData.get("startDate"),
                (LocalDateTime) reservationData.get("endDate")
            );
        } catch (RoomIsNotUsableException | RoomAlreadyReservedAtGivenDateAndTimeException | TimeOfReservationStartAfterEndTimeException e) {
            exceptionWasThrown = true;
            catchedException = e;
        }
    }

}
