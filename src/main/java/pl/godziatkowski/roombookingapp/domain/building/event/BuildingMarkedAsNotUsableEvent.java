package pl.godziatkowski.roombookingapp.domain.building.event;

import org.springframework.context.ApplicationEvent;

public class BuildingMarkedAsNotUsableEvent
    extends ApplicationEvent {

    private static final long serialVersionUID = -6050694023981774257L;
    private final Long buildingId;

    public BuildingMarkedAsNotUsableEvent(Object source, Long buildingId) {
        super(source);
        this.buildingId = buildingId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

}
