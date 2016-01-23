package pl.godziatkowski.roombookingapp.domain.building.finder;

import java.util.List;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;

public interface IBuildingSnapshotFinder {
    
    BuildingSnapshot findOneByNameAndAddressAndCity(String name, String address, String city);
    
    List<BuildingSnapshot> findAllUsable();
    
    List<BuildingSnapshot> findAll();

    BuildingSnapshot findOneById(Long buildingId);

}
