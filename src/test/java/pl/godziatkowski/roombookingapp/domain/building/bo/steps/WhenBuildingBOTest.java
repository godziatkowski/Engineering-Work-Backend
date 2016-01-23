package pl.godziatkowski.roombookingapp.domain.building.bo.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.bo.IBuildingBO;
import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.exception.BuildingAlreadyExistsException;

public class WhenBuildingBOTest
    extends Stage<WhenBuildingBOTest> {

    @ExpectedScenarioState
    private IBuildingBO buildingBO;
    @ExpectedScenarioState
    private Map<String, String> buildingData;
    @ExpectedScenarioState
    private Building building;

    @ProvidedScenarioState
    Boolean exceptionWasThrown;
    @ProvidedScenarioState
    private BuildingSnapshot buildingSnapshot;

    public void add_invoked() {
        exceptionWasThrown = false;
        try {
            buildingSnapshot = buildingBO.add(buildingData.get("name"), buildingData.get("name"),
                buildingData.get("name"));
        } catch (BuildingAlreadyExistsException exception) {
            exceptionWasThrown = true;
        }
    }

    public void edit_invoked() {
        exceptionWasThrown = false;
        try {
            buildingBO.edit(building.toSnapshot().getId(), buildingData.get("name"),
                buildingData.get("name"), buildingData.get("name"));
        } catch (BuildingAlreadyExistsException exception) {
            exceptionWasThrown = true;
        }
    }

    public void markAsUsable_invoked() {
        buildingBO.markAsUsable(building.toSnapshot().getId());
    }

    public void markAsNotUsable_invoked() {
        buildingBO.markAsNotUsable(building.toSnapshot().getId());
    }

}
