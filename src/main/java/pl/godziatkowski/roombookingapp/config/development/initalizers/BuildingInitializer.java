package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.building.bo.IBuildingBO;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Component
@Profile(Profiles.DEVELOPMENT)
public class BuildingInitializer
    implements IBuildingInitalizer {

    private final IBuildingBO buildingBO;

    @Autowired
    public BuildingInitializer(IBuildingBO buildingBO) {
        this.buildingBO = buildingBO;
    }

    @Override
    public List<Long> initializeBuildings() {
        List<Long> buildingIds = new ArrayList<>();

        buildingIds.add(buildingBO.add("WEEIA", "Bohdana Stefanowskiego 18/22", "Lodz").getId());
        buildingIds.add(buildingBO.add("Akwarium", "al. Politechniki 11", "Lodz").getId());
        buildingIds.add(buildingBO.add("CTI", "Wólczańska 217/223", "Lodz").getId());

        return buildingIds;
    }

}
