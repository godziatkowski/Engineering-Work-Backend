package pl.godziatkowski.roombookingapp.domain.room.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Room
    implements Serializable {

    private static final long serialVersionUID = 5924256619258999158L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @NotNull
    private Long buildingId;

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

    @NotNull
    private Boolean isUsable;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room", orphanRemoval = true)
    @OrderBy("startDate")
    private List<Reservation> reservations = new ArrayList<>();

    protected Room() {
    }

    protected Long getId() {
        if (id == null) {
            throw new EntityInStateNewException();
        }
        return id;
    }

    public Room(String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean hasProjector, Boolean hasBlackboard) {
        this.name = name;
        this.roomType = roomType;
        this.buildingId = buildingId;
        this.floor = floor;
        this.seatsCount = seatsCount;
        this.computerStationsCount = computerStationsCount;
        this.hasProjector = hasProjector;
        this.hasBlackboard = hasBlackboard;
        this.isUsable = true;
    }

    public void edit(String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean hasProjector, Boolean hasBlackboard) {
        this.name = name;
        this.roomType = roomType;
        this.buildingId = buildingId;
        this.floor = floor;
        this.seatsCount = seatsCount;
        this.computerStationsCount = computerStationsCount;
        this.hasProjector = hasProjector;
        this.hasBlackboard = hasBlackboard;
    }

    public void markAsUsable() {
        this.isUsable = true;
    }

    public void markAsNotUsable() {
        this.isUsable = false;
    }

    public RoomSnapshot toSnapshot() {
        if (id == null) {
            throw new EntityInStateNewException();
        }
        return new RoomSnapshot(id, name, roomType, buildingId, floor, seatsCount, computerStationsCount, hasProjector,
            hasBlackboard, isUsable);
    }

}
