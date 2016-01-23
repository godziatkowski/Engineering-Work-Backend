package pl.godziatkowski.roombookingapp.domain.building.entity.steps;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

public class GivenBuildingTest
    extends Stage<GivenBuildingTest> {

    @ExpectedScenarioState
    private IBuildingRepository buildingRepository;

    @ProvidedScenarioState
    private Map<String, String> buildingData = new HashMap<>();
    @ProvidedScenarioState
    private Building building;

    public GivenBuildingTest a_building_data(String stringData) {
        buildingData.put("name", stringData);
        buildingData.put("address", stringData);
        buildingData.put("city", stringData);

        return this;
    }

    public GivenBuildingTest an_existing_building(String stringData) {
        buildingData.put("name", stringData);
        buildingData.put("address", stringData);
        buildingData.put("city", stringData);
        building = new Building(stringData, stringData, stringData);
        buildingRepository.save(building);
        return this;
    }

    public void given_building_is_not_usable() {
        building.markAsNotUsable();
        building = buildingRepository.save(building);
    }

    public void not_persisted_building(String stringValue) {
        building = new Building(stringValue, stringValue, stringValue);
    }

}
