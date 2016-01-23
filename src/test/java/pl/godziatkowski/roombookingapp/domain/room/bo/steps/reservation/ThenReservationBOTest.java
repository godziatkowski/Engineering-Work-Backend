package pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation;

import java.time.LocalDateTime;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyReservedAtGivenDateAndTimeException;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomIsNotUsableException;
import pl.godziatkowski.roombookingapp.domain.room.exception.TimeOfReservationStartAfterEndTimeException;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenReservationBOTest
    extends Stage<ThenReservationBOTest> {

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ExpectedScenarioState
    private IReservationRepository reservationRepository;
    @ExpectedScenarioState
    private ReservationSnapshot reservationSnapshot;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;
    @ExpectedScenarioState
    private RuntimeException catchedException;
    @ExpectedScenarioState
    private Map<String, Object> reservationData;

    public ThenReservationBOTest reservation_should_be_created() {        
        assertThat(exceptionWasThrown, is(false));
        assertThat(catchedException, is(nullValue()));
        assertThat(reservationSnapshot, is(notNullValue()));
        assertThat(reservationSnapshot.getRoomSnapshot().getId(), is(equalTo((Long) reservationData.get("roomId"))));
        assertThat(reservationSnapshot.getUserId(), is(equalTo((Long) reservationData.get("userId"))));
        assertThat(reservationSnapshot.getStartDate(), is(equalTo((LocalDateTime) reservationData.get("startDate"))));
        assertThat(reservationSnapshot.getEndDate(), is(equalTo((LocalDateTime) reservationData.get("endDate"))));

        return this;
    }

    public ThenReservationBOTest exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        return this;
    }

    public ThenReservationBOTest exception_is_of_type_RoomIsNotUsableException() {
        assertThat(catchedException instanceof RoomIsNotUsableException, is(true));
        return this;
    }

    public ThenReservationBOTest exception_is_of_type_TimeOfReservationStartAfterEndTimeException() {
        assertThat(catchedException instanceof TimeOfReservationStartAfterEndTimeException, is(true));
        return this;
    }

    public ThenReservationBOTest exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException() {
        assertThat(catchedException instanceof RoomAlreadyReservedAtGivenDateAndTimeException, is(true));
        return this;
    }

    public ThenReservationBOTest reservation_should_be_edited() {
        reservationSnapshot = reservationRepository.findOne((Long) reservationData.get("reservationId")).toSnapshot();
        assertThat(exceptionWasThrown, is(false));
        assertThat(catchedException, is(nullValue()));
        assertThat(reservationSnapshot, is(notNullValue()));
        assertThat(reservationSnapshot.getRoomSnapshot().getId(), is(equalTo((Long) reservationData.get("roomId"))));
        assertThat(reservationSnapshot.getUserId(), is(equalTo((Long) reservationData.get("userId"))));
        assertThat(reservationSnapshot.getStartDate(), is(equalTo((LocalDateTime) reservationData.get("startDate"))));
        assertThat(reservationSnapshot.getEndDate(), is(equalTo((LocalDateTime) reservationData.get("endDate"))));
        return this;

    }

}
