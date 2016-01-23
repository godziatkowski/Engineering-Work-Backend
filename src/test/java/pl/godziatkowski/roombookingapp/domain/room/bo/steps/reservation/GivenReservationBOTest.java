package pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation;

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

public class GivenReservationBOTest
    extends Stage<GivenReservationBOTest> {

    private static final String CLAZZ = GivenReservationBOTest.class.getSimpleName();
    private static final Long LONG_VALUE = 10L;
    private static final Integer INTEGER_VALUE = 10;

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ExpectedScenarioState
    private IReservationRepository reservationRepository;

    @ProvidedScenarioState
    private Map<String, Object> reservationData = new HashMap<>();
    @ProvidedScenarioState
    private Room room;

    public GivenReservationBOTest a_room() {
        room = new Room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true, true);
        room = roomRepository.save(room);
        return this;
    }

    public GivenReservationBOTest a_reservation_data(Long userId,
        LocalDateTime startDate, LocalDateTime endDate) {
        reservationData.put("roomId", room.toSnapshot().getId());
        reservationData.put("userId", userId);
        reservationData.put("startDate", startDate);
        reservationData.put("endDate", endDate);
        return this;
    }

    public GivenReservationBOTest that_is_not_usable() {
        room.markAsNotUsable();
        room = roomRepository.save(room);
        return this;
    }

    public GivenReservationBOTest a_reservation_for_room(Long USER_ID, LocalDateTime NOW,
        LocalDateTime TWO_HOURS_FROM_NOW) {
        Reservation reservation = new Reservation(room, USER_ID, NOW, TWO_HOURS_FROM_NOW);
        reservation = reservationRepository.save(reservation);
        a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        reservationData.put("reservationId", reservation.toSnapshot().getId());
        return this;
    }

    public GivenReservationBOTest another_room() {
        room = new Room(CLAZZ + 2, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true, true);
        room = roomRepository.save(room);
        return this;
    }

}
