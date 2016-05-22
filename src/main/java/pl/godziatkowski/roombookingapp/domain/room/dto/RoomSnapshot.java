package pl.godziatkowski.roombookingapp.domain.room.dto;

import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;

public class RoomSnapshot {

    private final Long id;
    private final String name;
    private final RoomType roomType;
    private final Integer floor;
    private final Long seatsCount;
    private final Long computerStationsCount;
    private final Boolean hasProjector;
    private final Boolean hasBlackboard;
    private final Boolean isUsable;

    public RoomSnapshot(Long id, String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean hasProjector, Boolean hasBlackboard, Boolean isUsable) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.floor = floor;
        this.seatsCount = seatsCount;
        this.computerStationsCount = computerStationsCount;
        this.hasProjector = hasProjector;
        this.hasBlackboard = hasBlackboard;
        this.isUsable = isUsable;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Integer getFloor() {
        return floor;
    }

    public Long getSeatsCount() {
        return seatsCount;
    }

    public Long getComputerStationsCount() {
        return computerStationsCount;
    }

    public Boolean hasProjector() {
        return hasProjector;
    }

    public Boolean hasBlackboard() {
        return hasBlackboard;
    }

    public Boolean isUsable() {
        return isUsable;
    }

}
