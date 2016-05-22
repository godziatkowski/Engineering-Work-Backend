package pl.godziatkowski.roombookingapp.web.restapi.room;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomEdit {

    @NotNull
    private Long id;
    @NotNull
    @Size(min = 1, max = 150)
    private String name;
    @NotNull
    private RoomType roomType;
    @NotNull
    private Integer floor;
    @NotNull
    private Long seatsCount;
    @NotNull
    private Long computerStationsCount;
    @NotNull
    private Boolean hasProjector;
    @NotNull
    private Boolean hasBlackboard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Long getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(Long seatsCount) {
        this.seatsCount = seatsCount;
    }

    public Long getComputerStationsCount() {
        return computerStationsCount;
    }

    public void setComputerStationsCount(Long computerStationsCount) {
        this.computerStationsCount = computerStationsCount;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public Boolean getHasBlackboard() {
        return hasBlackboard;
    }

    public void setHasBlackboard(Boolean hasBlackboard) {
        this.hasBlackboard = hasBlackboard;
    }

}
