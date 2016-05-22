package pl.godziatkowski.roombookingapp.web.restapi.reservation;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.web.restapi.room.RoomType;

public class Room {

    private final Long id;
    private final String name;
    private final Integer floor;
    private final RoomType roomType;

    public Room(RoomSnapshot roomSnapshot) {
        this.id = roomSnapshot.getId();
        this.name = roomSnapshot.getName();
        this.floor = roomSnapshot.getFloor();
        this.roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.convertToRestapiValue(
            roomSnapshot.getRoomType());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFloor() {
        return floor;
    }

    public RoomType getRoomType() {
        return roomType;
    }

}
