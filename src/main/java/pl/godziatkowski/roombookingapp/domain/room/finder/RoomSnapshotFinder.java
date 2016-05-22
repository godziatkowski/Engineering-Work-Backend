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
    public List<RoomSnapshot> findAllByFloor(Integer floor) {
        List<Room> rooms = roomRepository.findAllByFloor(floor);
        return convert(rooms);
    }

    @Override
    public RoomSnapshot findOneByNameAndFloor(String name, Integer floor) {
        Room room = roomRepository.findOneByNameAndFloor(name, floor);
        return room != null ? room.toSnapshot() : null;
    }

    @Override
    public List<RoomSnapshot> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return convert(rooms);
    }
    
    @Override
    public List<Integer> findFloors(){
        return roomRepository.findFloors();
    }
    
    @Override
    public RoomSnapshot findOneByName(String name){
        Room room = roomRepository.findOneByName(name);
        return room != null ? room.toSnapshot() : null;
    }

    @Override
    public RoomSnapshot findOneById(Long id) {
        Room room = roomRepository.findOne(id);
        return room != null ? room.toSnapshot() : null;
    }

    private List<RoomSnapshot> convert(List<Room> rooms) {
        return rooms.stream()
            .map(Room::toSnapshot)
            .collect(Collectors.toList());
    }

}
