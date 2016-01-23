package pl.godziatkowski.roombookingapp.domain.room.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.Finder;

@Finder
public class RoomSnapshotFinder
    implements IRoomSnapshotFinder {

    private final IRoomRepository roomRepository;

    @Autowired
    public RoomSnapshotFinder(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomSnapshot> findUsable() {
        List<Room> rooms = roomRepository.findAllByIsUsableTrue();
            
        return convert(rooms);
    }

    @Override
    public List<RoomSnapshot> findAllByName(String name) {
        List<Room> rooms = roomRepository.findAllByName(name);
        return convert(rooms);
    }

    @Override
    public RoomSnapshot findOneByNameAndBuildingId(String name, Long buildingId) {
        Room room = roomRepository.findOneByBuildingIdAndName(buildingId, name);
        return room != null ? room.toSnapshot() : null;
    }

    @Override
    public List<RoomSnapshot> findAll() {
         List<Room> rooms = roomRepository.findAll();
         return convert(rooms);
    }

    @Override
    public RoomSnapshot findOneById(Long id) {
        Room room = roomRepository.findOne(id);
        return room != null ? room.toSnapshot() : null;
    }

    @Override
    public List<RoomSnapshot> findAllByBuildingId(Long buildingId) {
        List<Room> rooms = roomRepository.findAllByBuildingId(buildingId);
        return convert(rooms);
    }

    private List<RoomSnapshot> convert(List<Room> rooms) {
        return rooms.stream()
            .map(Room::toSnapshot)
            .collect(Collectors.toList());
    }

}
