package pl.godziatkowski.roombookingapp.domain.room.finder;

import java.util.List;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;

public interface IRoomSnapshotFinder {

    List<RoomSnapshot> findUsable();
    
    List<RoomSnapshot> findAll();

    RoomSnapshot findOneByNameAndFloor(String name, Integer floor);

    RoomSnapshot findOneById(Long id);

    List<RoomSnapshot> findAllByFloor(Integer floor);
    
    List<Integer> findFloors();

    RoomSnapshot findOneByName(String name);
    
}
