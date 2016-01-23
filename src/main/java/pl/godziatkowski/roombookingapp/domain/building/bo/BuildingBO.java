package pl.godziatkowski.roombookingapp.domain.building.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.event.BuildingMarkedAsNotUsableEvent;
import pl.godziatkowski.roombookingapp.domain.building.exception.BuildingAlreadyExistsException;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.BusinessObject;

@BusinessObject
public class BuildingBO
    implements IBuildingBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingBO.class);

    private final IBuildingRepository buildingRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public BuildingBO(IBuildingRepository buildingRepository, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public BuildingSnapshot add(String name, String address, String city) {
        if (buildingRepository.findOneByNameAndCityAndAddress(name, city, address) != null) {
            throw new BuildingAlreadyExistsException();
        }
        Building building = new Building(name, address, city);
        building = buildingRepository.save(building);
        BuildingSnapshot buildingSnapshot = building.toSnapshot();
        LOGGER.info("Created building with id <{}> and name <{}> at <{}> in <{}> city",
            buildingSnapshot.getId(), buildingSnapshot.getName(),
            buildingSnapshot.getAddress(), buildingSnapshot.getCity());

        return buildingSnapshot;
    }

    @Override
    public void edit(Long id, String name, String address, String city) {
        Building building;
        if ((building = buildingRepository.findOneByNameAndCityAndAddress(name, city, address)) != null) {
            if (!building.toSnapshot().getId().equals(id)) {
                throw new BuildingAlreadyExistsException();
            }
        } else {
            building = buildingRepository.findOne(id);
        }
        building.edit(name, address, city);
        buildingRepository.save(building);
        LOGGER.info("Properties of building with id <{}> changed to: <{}> at <{}> in <{}> city", id, name, address, city);
    }

    @Override
    public void markAsUsable(Long id) {
        Building building = buildingRepository.findOne(id);
        if (building != null) {
            building.markAsUsable();
            buildingRepository.save(building);
            LOGGER.info("Building with id <{}> marked as usable");
        }
    }

    @Override
    public void markAsNotUsable(Long id) {
        Building building = buildingRepository.findOne(id);
        if (building != null) {
            building.markAsNotUsable();
            buildingRepository.save(building);
            LOGGER.info("Building with id <{}> marked as usable");
            BuildingMarkedAsNotUsableEvent event = new BuildingMarkedAsNotUsableEvent(this, id);
            eventPublisher.publishEvent(event);
        }
    }

}
