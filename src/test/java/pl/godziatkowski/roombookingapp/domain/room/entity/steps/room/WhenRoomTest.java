package pl.godziatkowski.roombookingapp.domain.room.entity.steps.room;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

public class WhenRoomTest
    extends Stage<WhenRoomTest> {

    @ExpectedScenarioState
    private Room room;
    @ExpectedScenarioState
    private Map<String, Object> roomData;

    @ProvidedScenarioState
    private Boolean exceptionWasThrown;
    @ProvidedScenarioState
    private RoomSnapshot roomSnapshot;

    public void edit_invoked() {

        room.edit((String) roomData.get("name"),
            (RoomType) roomData.get("roomType"),
            (Integer) roomData.get("floor"),
            (Long) roomData.get("seatsCount"),
            (Long) roomData.get("computerStationsCount"),
            (Boolean) roomData.get("hasProjector"),
            (Boolean) roomData.get("hasBlackboard"));
    }

    public void mark_as_usable_invoked() {
        room.markAsUsable();
    }

    public void mark_as_not_usable_invoked() {
        room.markAsNotUsable();
    }

    public void to_snapshot_invoked() {
        exceptionWasThrown = false;

        try {
            roomSnapshot = room.toSnapshot();
        } catch (EntityInStateNewException e) {
            exceptionWasThrown = true;
        }
    }

}
