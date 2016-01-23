package pl.godziatkowski.roombookingapp.domain.building.entity.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenBuildingTest
    extends Stage<ThenBuildingTest> {

    @ExpectedScenarioState
    private IBuildingRepository buildingRepository;
    @ExpectedScenarioState
    private Map<String, String> buildingData;
    @ExpectedScenarioState
    private Building building;
    @ExpectedScenarioState
    private BuildingSnapshot buildingSnapshot;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;

    public ThenBuildingTest buildin_should_be_edited() {
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot.getName(), is(equalTo(buildingData.get("name"))));
        assertThat(buildingSnapshot.getAddress(), is(equalTo(buildingData.get("address"))));
        assertThat(buildingSnapshot.getCity(), is(equalTo(buildingData.get("city"))));
        return this;
    }

    public ThenBuildingTest building_should_be_marked_as_usable() {
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot.isUsable(), is(true));
        return this;
    }

    public ThenBuildingTest building_should_be_marked_as_not_usable() {
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot.isUsable(), is(false));
        return this;
    }

    public ThenBuildingTest snapshot_should_be_returned() {
        assertThat(exceptionWasThrown, is(false));
        assertThat(buildingSnapshot, is(notNullValue()));
        assertThat(buildingSnapshot.getName(), is(equalTo(buildingData.get("name"))));
        assertThat(buildingSnapshot.getAddress(), is(equalTo(buildingData.get("address"))));
        assertThat(buildingSnapshot.getCity(), is(equalTo(buildingData.get("city"))));
        return this;
    }

    public ThenBuildingTest exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        return this;
    }

}
