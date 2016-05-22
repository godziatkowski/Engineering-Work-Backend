package pl.godziatkowski.roombookingapp.domain.room.entity.steps.room;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

public class GivenRoomTest
    extends Stage<GivenRoomTest> {

    @ExpectedScenarioState
    private IRoomRepository roomRepository;

    @ProvidedScenarioState
    private Room room;
    @ProvidedScenarioState
    Map<String, Object> roomData = new HashMap<>();
    @ProvidedScenarioState
    private Long existingRoomId;

    public GivenRoomTest an_existing_room(String name, RoomType roomType, Integer floor,
        Long seatsCount, Long computerStationsCount, boolean hasProjector, boolean hasBlackboard) {

        roomData.put("name", name);
        roomData.put("roomType", roomType);
        roomData.put("floor", floor);
        roomData.put("seatsCount", seatsCount);
        roomData.put("computerStationsCount", computerStationsCount);
        roomData.put("hasProjector", hasProjector);
        roomData.put("hasBlackboard", hasBlackboard);

        room = new Room(name, roomType, floor, seatsCount,
            computerStationsCount, hasProjector, hasBlackboard);
        room = roomRepository.save(room);
        existingRoomId = room.toSnapshot().getId();
        return this;
    }

    public GivenRoomTest room_data(String name, RoomType roomType, Integer floor,
        Long seatsCount, Long computerStationsCount, boolean hasProjector, boolean hasBlackboard) {
        roomData.put("name", name);
        roomData.put("roomType", roomType);
        roomData.put("floor", floor);
        roomData.put("seatsCount", seatsCount);
        roomData.put("computerStationsCount", computerStationsCount);
        roomData.put("hasProjector", hasProjector);
        roomData.put("hasBlackboard", hasBlackboard);
        return this;
    }

    public GivenRoomTest room_is_marked_as_not_usable() {
        room.markAsNotUsable();
        room = roomRepository.save(room);
        return this;
    }

    public void not_persisted_room(String name, RoomType roomType, Integer floor,
        Long seatsCount, Long computerStationsCount, boolean hasProjector, boolean hasBlackboard) {
        room = new Room(name, roomType, floor, seatsCount,
            computerStationsCount, hasProjector, hasBlackboard);
    }

}
