package pl.godziatkowski.roombookingapp.domain.room.finder;

import java.util.List;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;

public interface IRoomSnapshotFinder {

    List<RoomSnapshot> findUsable();
    
    List<RoomSnapshot> findAll();

    List<RoomSnapshot> findAllByName(String name);
    
    RoomSnapshot findOneByNameAndBuildingId(String name, Long buildingId);

    RoomSnapshot findOneById(Long id);
    
    List<RoomSnapshot> findAllByBuildingId(Long buildingId);

}
