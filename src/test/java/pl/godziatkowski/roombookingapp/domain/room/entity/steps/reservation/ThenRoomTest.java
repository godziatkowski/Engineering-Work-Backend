package pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenRoomTest
    extends Stage<ThenRoomTest> {

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ExpectedScenarioState
    private Room room;
    @ExpectedScenarioState
    Map<String, Object> roomData = new HashMap<>();
    @ExpectedScenarioState
    private Long existingRoomId;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;
    @ExpectedScenarioState
    private RoomSnapshot roomSnapshot;

    public void room_should_be_edited() {
        room = roomRepository.save(room);
        RoomSnapshot roomSnapshot = room.toSnapshot();
        assertThat(roomSnapshot.getId(), is(equalTo(existingRoomId)));
        assertThat(roomSnapshot.getName(), is(equalTo((String) roomData.get("name"))));
        assertThat(roomSnapshot.getRoomType(), is(equalTo((RoomType) roomData.get("roomType"))));
        assertThat(roomSnapshot.getBuildingId(), is(equalTo((Long) roomData.get("buildingId"))));
        assertThat(roomSnapshot.getFloor(), is(equalTo((Integer) roomData.get("floor"))));
        assertThat(roomSnapshot.getSeatsCount(), is(equalTo((Long) roomData.get("seatsCount"))));
        assertThat(roomSnapshot.getComputerStationsCount(), is(equalTo((Long) roomData.get("computerStationsCount"))));
        assertThat(roomSnapshot.hasProjector(), is(equalTo((Boolean) roomData.get("hasProjector"))));
        assertThat(roomSnapshot.hasBlackboard(), is(equalTo((Boolean) roomData.get("hasBlackboard"))));
    }

    public void room_should_be_marked_as_not_usable() {
        room = roomRepository.save(room);
        RoomSnapshot roomSnapshot = room.toSnapshot();
        assertThat(roomSnapshot.isUsable(), is(false));
    }

    public void room_should_be_marked_as_usable() {
        room = roomRepository.save(room);
        RoomSnapshot roomSnapshot = room.toSnapshot();
        assertThat(roomSnapshot.isUsable(), is(true));
    }

    public void snapshot_should_be_returned() {
        assertThat(exceptionWasThrown, is(false));
        assertThat(roomSnapshot.getId(), is(equalTo(existingRoomId)));
        assertThat(roomSnapshot.getName(), is(equalTo((String) roomData.get("name"))));
        assertThat(roomSnapshot.getRoomType(), is(equalTo((RoomType) roomData.get("roomType"))));
        assertThat(roomSnapshot.getBuildingId(), is(equalTo((Long) roomData.get("buildingId"))));
        assertThat(roomSnapshot.getFloor(), is(equalTo((Integer) roomData.get("floor"))));
        assertThat(roomSnapshot.getSeatsCount(), is(equalTo((Long) roomData.get("seatsCount"))));
        assertThat(roomSnapshot.getComputerStationsCount(), is(equalTo((Long) roomData.get("computerStationsCount"))));
        assertThat(roomSnapshot.hasProjector(), is(equalTo((Boolean) roomData.get("hasProjector"))));
        assertThat(roomSnapshot.hasBlackboard(), is(equalTo((Boolean) roomData.get("hasBlackboard"))));
    }

    public void exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        assertThat(roomSnapshot, is(nullValue()));
    }

}
