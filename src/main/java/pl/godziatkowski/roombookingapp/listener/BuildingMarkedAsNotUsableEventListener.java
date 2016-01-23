package pl.godziatkowski.roombookingapp.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import pl.godziatkowski.roombookingapp.domain.building.event.BuildingMarkedAsNotUsableEvent;
import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;

public class BuildingMarkedAsNotUsableEventListener
    implements ApplicationListener<BuildingMarkedAsNotUsableEvent> {

    private final IRoomBO roomBO;

    @Autowired
    public BuildingMarkedAsNotUsableEventListener(IRoomBO roomBO) {
        this.roomBO = roomBO;
    }

    @Override
    public void onApplicationEvent(BuildingMarkedAsNotUsableEvent event) {
        roomBO.markRoomsInBuildingAsNotUsable(event.getBuildingId());
    }

}
