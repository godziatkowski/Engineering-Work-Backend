package pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation;

import java.time.LocalDateTime;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenReservationTest
    extends Stage<ThenReservationTest> {

    @ExpectedScenarioState
    private IReservationRepository reservationRepository;

    @ExpectedScenarioState
    private Reservation reservation;

    @ExpectedScenarioState
    private ReservationSnapshot reservationSnapshot;

    @ExpectedScenarioState
    private Boolean exceptionWasThrown;

    @ExpectedScenarioState
    private Long reservationId;

    @ExpectedScenarioState
    private Map<String, Object> reservationData;

    public void reservation_should_be_edited() {
        reservation = reservationRepository.save(reservation);
        ReservationSnapshot reservationSnapshot = reservation.toSnapshot();
        assertThat(reservationSnapshot.getRoomSnapshot().getId(),
            is(equalTo(((Room) reservationData.get("room")).toSnapshot().getId())));
        assertThat(reservationSnapshot.getStartDate(), is(equalTo((LocalDateTime) reservationData.get("startDate"))));
        assertThat(reservationSnapshot.getEndDate(), is(equalTo((LocalDateTime) reservationData.get("endDate"))));
    }

    public void reservation_should_be_canceled() {
        reservation = reservationRepository.save(reservation);
        ReservationSnapshot reservationSnapshot = reservation.toSnapshot();
        assertThat(reservationSnapshot.isCanceled(), is(true));
    }

    public void snapshot_should_be_returned() {
        assertThat(exceptionWasThrown, is(false));
        assertThat(reservationSnapshot.getId(), is(equalTo(reservationId)));
        assertThat(reservationSnapshot.getRoomSnapshot().getId(),
            is(equalTo(((Room) reservationData.get("room")).toSnapshot().getId())));
        assertThat(reservationSnapshot.getStartDate(), is(equalTo((LocalDateTime) reservationData.get("startDate"))));
        assertThat(reservationSnapshot.getEndDate(), is(equalTo((LocalDateTime) reservationData.get("endDate"))));

    }

    public void entityInStateNew_exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        assertThat(reservationSnapshot, is(nullValue()));
    }

}
