package pl.godziatkowski.roombookingapp.domain.room.bo.steps.room;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

public class GivenRoomBOTest
    extends Stage<GivenRoomBOTest> {

    @ExpectedScenarioState
    private IRoomRepository roomRepository;
    @ProvidedScenarioState
    private Map<String, Object> roomData = new HashMap<>();
    @ProvidedScenarioState
    private RoomSnapshot roomSnapshot;

    public GivenRoomBOTest roomData(String name, RoomType roomType, long buildingId, int floor,
        long seatsCount, long computerStationsCount, boolean hasProjector, boolean hasBlackboard) {
        roomData.put("name", name);
        roomData.put("roomType", roomType);
        roomData.put("buildingId", buildingId);
        roomData.put("floor", floor);
        roomData.put("seatsCount", seatsCount);
        roomData.put("computerStationsCount", computerStationsCount);
        roomData.put("hasProjector", hasProjector);
        roomData.put("hasBlackboard", hasBlackboard);
        return this;
    }

    public GivenRoomBOTest an_existing_room(String name, RoomType roomType, long buildingId, int floor,
        long seatsCount, long computerStationsCount, boolean hasProjector, boolean hasBlackboard) {
        roomData.put("name", name);
        roomData.put("roomType", roomType);
        roomData.put("buildingId", buildingId);
        roomData.put("floor", floor);
        roomData.put("seatsCount", seatsCount);
        roomData.put("computerStationsCount", computerStationsCount);
        roomData.put("hasProjector", hasProjector);
        roomData.put("hasBlackboard", hasBlackboard);

        Room room = new Room(name, roomType, buildingId, floor, seatsCount, computerStationsCount, hasProjector,
            hasBlackboard);
        roomSnapshot = roomRepository.save(room).toSnapshot();
        return this;
    }

    public void room_is_not_usable() {
        Room room = roomRepository.findOne(roomSnapshot.getId());
        room.markAsNotUsable();
        roomRepository.save(room);
    }

}
