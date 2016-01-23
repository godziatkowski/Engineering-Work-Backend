package pl.godziatkowski.roombookingapp.domain.building.bo.steps;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

public class GivenBuildingBOTest
    extends Stage<GivenBuildingBOTest> {

    @ExpectedScenarioState
    private IBuildingRepository buildingRepository;
    @ExpectedScenarioState
    private Building building;

    @ProvidedScenarioState
    private Map<String, String> buildingData = new HashMap<>();
    @ProvidedScenarioState
    private Integer expectedBuildingsCount;

    public GivenBuildingBOTest building_data(String stringData) {
        buildingData.put("name", stringData);
        buildingData.put("address", stringData);
        buildingData.put("city", stringData);
        return this;
    }

    public GivenBuildingBOTest persisted_building(String stringData) {
        building = new Building(stringData, stringData, stringData);
        building = buildingRepository.save(building);
        return this;
    }

    public GivenBuildingBOTest expectedBuildingsCount() {
        expectedBuildingsCount = (int) buildingRepository.count() + 1;
        return this;
    }

    public GivenBuildingBOTest building_is_not_usable() {
        building.markAsNotUsable();
        building = buildingRepository.save(building);
        return this;
    }

}
