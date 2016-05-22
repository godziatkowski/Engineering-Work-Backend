package pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

public class GivenReservationTest
    extends Stage<GivenReservationTest> {

    private static final String CLAZZ = GivenReservationTest.class.getSimpleName();
    private static final Long LONG_VALUE = 10L;
    private static final Integer INTEGER_VALUE = 10;

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ExpectedScenarioState
    private IReservationRepository reservationRepository;

    private Room room;
    @ProvidedScenarioState
    private Long reservationId;
    @ProvidedScenarioState
    private Reservation reservation;
    @ProvidedScenarioState
    private Map<String, Object> reservationData = new HashMap<>();

    public GivenReservationTest a_room() {
        room = new Room(CLAZZ, RoomType.LABORATORY, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true, true);
        room = roomRepository.save(room);
        return this;
    }

    public GivenReservationTest a_reservation(Long userId, LocalDateTime from, LocalDateTime to) {
        reservation_data(userId, from, to);
        reservation = new Reservation(room, userId, from, to);
        reservation = reservationRepository.save(reservation);
        reservationId = reservation.toSnapshot().getId();
        return this;
    }

    public GivenReservationTest reservation_data(Long userId, LocalDateTime from, LocalDateTime to) {
        reservationData.put("room", room);
        reservationData.put("userId", room);
        reservationData.put("startDate", from);
        reservationData.put("endDate", to);
        return this;
    }

    public GivenReservationTest a_not_persisted_reservation(Long userId, LocalDateTime from, LocalDateTime to) {
        reservation_data(userId, from, to);
        reservation = new Reservation(room, userId, from, to);
        return this;
    }

}
