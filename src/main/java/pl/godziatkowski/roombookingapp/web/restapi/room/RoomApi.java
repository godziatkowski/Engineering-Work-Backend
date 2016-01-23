package pl.godziatkowski.roombookingapp.web.restapi.room;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;
import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/room")
public class RoomApi {

    private final IRoomBO roomBO;
    private final IRoomSnapshotFinder roomSnapshotFinder;
    private final IBuildingSnapshotFinder buildingSnapshotFinder;
    private final Validator roomNewValidator;
    private final Validator roomEditValidator;

    @Autowired
    public RoomApi(IRoomBO roomBO, IRoomSnapshotFinder roomSnapshotFinder,
        IBuildingSnapshotFinder buildingSnapshotFinder,
        @Qualifier("roomNewValidator") Validator roomNewValidator,
        @Qualifier("roomEditValidator") Validator roomEditValidator) {
        this.roomBO = roomBO;
        this.roomSnapshotFinder = roomSnapshotFinder;
        this.buildingSnapshotFinder = buildingSnapshotFinder;
        this.roomNewValidator = roomNewValidator;
        this.roomEditValidator = roomEditValidator;
    }

    @InitBinder("roomNew")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(roomNewValidator);
    }

    @InitBinder("roomEdit")
    protected void initEditBinder(WebDataBinder binder) {
        binder.setValidator(roomEditValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<Room>> list() {
        List<RoomSnapshot> roomSnapshots = roomSnapshotFinder.findAll();
        List<Room> rooms = roomSnapshots.stream().map(Room::new).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(rooms);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usable")
    public HttpEntity<List<Room>> listUsable() {
        List<RoomSnapshot> roomSnapshots = roomSnapshotFinder.findUsable();
        List<Room> rooms = roomSnapshots.stream().map(Room::new).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(rooms);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{buildingId}/rooms")
    public HttpEntity<List<Room>> listForBuilding(@PathVariable("buildingId") Long buildingId) {
        List<RoomSnapshot> roomSnapshots = roomSnapshotFinder.findAllByBuildingId(buildingId);
        List<Room> rooms = roomSnapshots.stream().map(Room::new).collect(Collectors.toList());
        return ResponseEntity
            .ok()
            .body(rooms);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<Room> get(@PathVariable("id") Long id) {
        RoomSnapshot roomSnapshot = roomSnapshotFinder.findOneById(id);
        if (roomSnapshot == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BuildingSnapshot buildingSnapshot = buildingSnapshotFinder.findOneById(roomSnapshot.getBuildingId());

        return ResponseEntity
            .ok()
            .body(new Room(roomSnapshot, buildingSnapshot));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Room> add(@Valid @RequestBody RoomNew roomNew) {
        RoomSnapshot roomSnapshot = roomBO.add(
            roomNew.getName(),
            RoomType.convertToDomainValue(roomNew.getRoomType()),
            roomNew.getBuildingId(),
            roomNew.getFloor(),
            roomNew.getSeatsCount(),
            roomNew.getComputerStationsCount(),
            roomNew.getHasProjector(),
            roomNew.getHasBlackboard());

        return ResponseEntity
            .ok()
            .body(new Room(roomSnapshot));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Room> edit(@Valid @RequestBody RoomEdit roomEdit) {
        roomBO.edit(
            roomEdit.getId(),
            roomEdit.getName(),
            RoomType.convertToDomainValue(roomEdit.getRoomType()),
            roomEdit.getBuildingId(),
            roomEdit.getFloor(),
            roomEdit.getSeatsCount(),
            roomEdit.getComputerStationsCount(),
            roomEdit.getHasProjector(),
            roomEdit.getHasBlackboard());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/markAsUsable")
    public HttpEntity<Room> markAsUsable(@PathVariable("id") Long id) {
        roomBO.markAsUsable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/markAsNotUsable")
    public HttpEntity<Room> markAsNotUsable(@PathVariable("id") Long id) {
        roomBO.markAsNotUsable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
