package pl.godziatkowski.roombookingapp.web.restapi.room;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class Room {

    private final Long id;
    private final String name;
    private final RoomType roomType;
    private final Integer floor;
    private final Long seatsCount;
    private final Long computerStationsCount;
    private final Boolean hasProjector;
    private final Boolean hasBlackboard;
    private final Boolean isUsable;
    private final Keeper keeper;

    public Room(RoomSnapshot roomSnapshot) {
        this.id = roomSnapshot.getId();
        this.name = roomSnapshot.getName();
        this.roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.convertToRestapiValue(
            roomSnapshot.getRoomType());
        this.floor = roomSnapshot.getFloor();
        this.seatsCount = roomSnapshot.getSeatsCount();
        this.computerStationsCount = roomSnapshot.getComputerStationsCount();
        this.hasProjector = roomSnapshot.hasProjector();
        this.hasBlackboard = roomSnapshot.hasBlackboard();
        this.isUsable = roomSnapshot.isUsable();
        this.keeper = null;
    }

    public Room(RoomSnapshot roomSnapshot, UserSnapshot userSnapshot) {
        this.id = roomSnapshot.getId();
        this.name = roomSnapshot.getName();
        this.roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.convertToRestapiValue(
            roomSnapshot.getRoomType());
        this.floor = roomSnapshot.getFloor();
        this.seatsCount = roomSnapshot.getSeatsCount();
        this.computerStationsCount = roomSnapshot.getComputerStationsCount();
        this.hasProjector = roomSnapshot.hasProjector();
        this.hasBlackboard = roomSnapshot.hasBlackboard();
        this.isUsable = roomSnapshot.isUsable();
        this.keeper = new Keeper(userSnapshot);
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

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public Boolean getHasBlackboard() {
        return hasBlackboard;
    }

    public Boolean getIsUsable() {
        return isUsable;
    }

    public Keeper getKeeper() {
        return keeper;
    }

}
