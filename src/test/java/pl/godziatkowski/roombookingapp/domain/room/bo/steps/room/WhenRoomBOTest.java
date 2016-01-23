package pl.godziatkowski.roombookingapp.domain.room.bo.steps.room;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;
import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyExistsException;

public class WhenRoomBOTest
    extends Stage<WhenRoomBOTest> {

    @ExpectedScenarioState
    private IRoomBO roomBO;
    @ExpectedScenarioState
    private Map<String, Object> roomData;
    @ExpectedScenarioState
    @ProvidedScenarioState
    private RoomSnapshot roomSnapshot;
    @ProvidedScenarioState
    private Boolean exceptionWasThrown;

    public void add_invoked_with_given_room_data() {
        exceptionWasThrown = false;
        try {
            roomSnapshot = roomBO.add(
                (String) roomData.get("name"),
                (RoomType) roomData.get("roomType"),
                (Long) roomData.get("buildingId"),
                (Integer) roomData.get("floor"),
                (Long) roomData.get("seatsCount"),
                (Long) roomData.get("computerStationsCount"),
                (Boolean) roomData.get("hasProjector"),
                (Boolean) roomData.get("hasBlackboard")
            );
        } catch (RoomAlreadyExistsException e) {
            exceptionWasThrown = true;
        }
    }

    public void edit_invoked() {
        exceptionWasThrown = false;
        try {
            roomBO.edit(roomSnapshot.getId(),
                (String) roomData.get("name"),
                (RoomType) roomData.get("roomType"),
                (Long) roomData.get("buildingId"),
                (Integer) roomData.get("floor"),
                (Long) roomData.get("seatsCount"),
                (Long) roomData.get("computerStationsCount"),
                (Boolean) roomData.get("hasProjector"),
                (Boolean) roomData.get("hasBlackboard")
            );
        } catch (RoomAlreadyExistsException e) {
            exceptionWasThrown = true;
        }
    }

    public void mark_as_usable_invoked() {
        roomBO.markAsUsable(roomSnapshot.getId());
    }

    public void mark_as_not_usable_invoked() {
        roomBO.markAsNotUsable(roomSnapshot.getId());
    }

}
