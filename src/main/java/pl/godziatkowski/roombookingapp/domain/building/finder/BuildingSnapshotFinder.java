package pl.godziatkowski.roombookingapp.domain.building.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.Finder;

@Finder
public class BuildingSnapshotFinder
    implements IBuildingSnapshotFinder {

    private final IBuildingRepository buildingRepository;

    @Autowired
    public BuildingSnapshotFinder(IBuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public BuildingSnapshot findOneByNameAndAddressAndCity(String name, String address, String city) {
        Building building = buildingRepository.findOneByNameAndCityAndAddress(name, city, address);

        return building != null ? building.toSnapshot() : null;
    }

    @Override
    public List<BuildingSnapshot> findAllUsable() {
        return buildingRepository.findAllByIsUsableTrue()
            .stream()
            .map(Building::toSnapshot)
            .collect(Collectors.toList());

    }

    @Override
    public List<BuildingSnapshot> findAll() {
        return buildingRepository.findAll()
            .stream()
            .map(Building::toSnapshot)
            .collect(Collectors.toList());
    }

    @Override
    public BuildingSnapshot findOneById(Long id) {
        Building building = buildingRepository.findOne(id);

        return building != null ? building.toSnapshot() : null;
    }

}
