package pl.godziatkowski.roombookingapp.web.restapi.room;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;
import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/room")
public class RoomApi {

    private final IRoomBO roomBO;
    private final IRoomSnapshotFinder roomSnapshotFinder;
    private final IUserSnapshotFinder userSnapshotFinder;
    private final Validator roomNewValidator;
    private final Validator roomEditValidator;

    @Autowired
    public RoomApi(IRoomBO roomBO, IRoomSnapshotFinder roomSnapshotFinder,
        IUserSnapshotFinder userSnapshotFinder,
        @Qualifier("roomNewValidator") Validator roomNewValidator,
        @Qualifier("roomEditValidator") Validator roomEditValidator) {
        this.roomBO = roomBO;
        this.roomSnapshotFinder = roomSnapshotFinder;
        this.userSnapshotFinder = userSnapshotFinder;
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
        Set<Long> keeperIds = roomSnapshots.stream()
            .map(RoomSnapshot::getKeeperId)
            .filter(id -> id != null)
            .collect(Collectors.toSet());
        Map<Long, UserSnapshot> keepers = userSnapshotFinder.findAsMapByUserIdIn(keeperIds);
        List<Room> rooms = roomSnapshots.stream()
            .map(roomSnapshot -> {
                if (roomSnapshot.getKeeperId() != null) {
                    return new Room(roomSnapshot, keepers.get(roomSnapshot.getKeeperId()));
                } else {
                    return new Room(roomSnapshot);
                }
            })
            .collect(Collectors.toList());

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

        return ResponseEntity
            .ok()
            .body(new Room(roomSnapshot));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usable")
    public HttpEntity<List<Room>> listUsable() {
        List<RoomSnapshot> roomSnapshots = roomSnapshotFinder.findUsable();
        List<Room> rooms = roomSnapshots.stream().map(Room::new).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(rooms);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/floors")
    public HttpEntity<List<Integer>> floors() {
        List<Integer> floors = roomSnapshotFinder.findFloors();

        return ResponseEntity
            .ok()
            .body(floors);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{floor}/rooms")
    public HttpEntity<List<Room>> roomsOnFloor(@PathVariable("floor") Integer floor) {
        List<RoomSnapshot> roomSnapshots = roomSnapshotFinder.findAllByFloor(floor);
        List<Room> rooms = roomSnapshots.stream().map(Room::new).collect(Collectors.toList());
        return ResponseEntity
            .ok()
            .body(rooms);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Room> add(@Valid @RequestBody RoomNew roomNew) {
        RoomSnapshot roomSnapshot = roomBO.add(
            roomNew.getName(),
            RoomType.convertToDomainValue(roomNew.getRoomType()),
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

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/assignKeeper")
    public HttpEntity<Void> setKeeper(@PathVariable("id") long id, @RequestParam("userId") long userId) {
        roomBO.assaignKeeper(id, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/clearKeeper")
    public HttpEntity<Void> clearKeeper(@PathVariable("id") long id) {
        roomBO.clearKeeper(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
