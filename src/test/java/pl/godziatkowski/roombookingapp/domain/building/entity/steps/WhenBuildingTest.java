package pl.godziatkowski.roombookingapp.domain.building.entity.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

public class WhenBuildingTest
    extends Stage<WhenBuildingTest> {

    @ExpectedScenarioState
    private Map<String, String> buildingData;
    @ExpectedScenarioState
    private Building building;

    @ProvidedScenarioState
    private Boolean exceptionWasThrown;
    @ProvidedScenarioState
    private BuildingSnapshot buildingSnapshot;

    public void edit_invoked() {
        building.edit(buildingData.get("name"), buildingData.get("address"), buildingData.get("city"));
    }

    public void mark_as_usable_invoked() {
        building.markAsUsable();
    }

    public void mark_as_not_usabl_invoked() {
        building.markAsNotUsable();
    }

    public void toSnapshot_invoked() {
        exceptionWasThrown = false;
        try {
            buildingSnapshot = building.toSnapshot();
        } catch (EntityInStateNewException e) {
            exceptionWasThrown = true;
        }
    }

}
