package pl.godziatkowski.roombookingapp.domain.room.bo.steps.room;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenRoomBOTest
    extends Stage<ThenRoomBOTest> {

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ExpectedScenarioState
    private Map<String, Object> roomData;
    @ExpectedScenarioState
    private RoomSnapshot roomSnapshot;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;

    public void room_should_be_added() {
        assertThat(exceptionWasThrown, is(false));
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
    }

    public void room_should_be_edited() {
        assertThat(exceptionWasThrown, is(false));
        roomSnapshot = roomRepository.findOne(roomSnapshot.getId()).toSnapshot();
        assertThat(roomSnapshot.getName(), is(equalTo((String) roomData.get("name"))));
        assertThat(roomSnapshot.getRoomType(), is(equalTo((RoomType) roomData.get("roomType"))));
        assertThat(roomSnapshot.getBuildingId(), is(equalTo((Long) roomData.get("buildingId"))));
        assertThat(roomSnapshot.getFloor(), is(equalTo((Integer) roomData.get("floor"))));
        assertThat(roomSnapshot.getSeatsCount(), is(equalTo((Long) roomData.get("seatsCount"))));
        assertThat(roomSnapshot.getComputerStationsCount(), is(equalTo((Long) roomData.get("computerStationsCount"))));
        assertThat(roomSnapshot.hasProjector(), is(equalTo((Boolean) roomData.get("hasProjector"))));
        assertThat(roomSnapshot.hasBlackboard(), is(equalTo((Boolean) roomData.get("hasBlackboard"))));
    }

    public void room_should_be_marked_as_usable() {
        roomSnapshot = roomRepository.findOne(roomSnapshot.getId()).toSnapshot();
        assertThat(roomSnapshot.isUsable(), is(true));

    }

    public void room_should_be_marked_as_not_usable() {
        roomSnapshot = roomRepository.findOne(roomSnapshot.getId()).toSnapshot();
        assertThat(roomSnapshot.isUsable(), is(false));
    }

}
