package pl.godziatkowski.roombookingapp.domain.building.bo.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.entity.Building;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenBuildingBOTest
    extends Stage<ThenBuildingBOTest> {

    @ExpectedScenarioState
    private Building building;
    @ExpectedScenarioState
    private Integer expectedBuildingsCount;
    @ExpectedScenarioState
    private IBuildingRepository buildingRepository;
    @ExpectedScenarioState
    private BuildingSnapshot buildingSnapshot;
    @ExpectedScenarioState
    private Map<String, String> buildingData;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;

    public ThenBuildingBOTest should_create_building() {
        assertThat(buildingRepository.findAll().size(), is(equalTo(expectedBuildingsCount)));
        return this;
    }

    public ThenBuildingBOTest return_snapshot() {
        assertThat(buildingSnapshot.getName(), is(equalTo(buildingData.get("name"))));
        assertThat(buildingSnapshot.getAddress(), is(equalTo(buildingData.get("address"))));
        assertThat(buildingSnapshot.getCity(), is(equalTo(buildingData.get("city"))));
        return this;
    }

    public ThenBuildingBOTest building_should_be_edited() {
        assertThat(exceptionWasThrown, is(false));
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot, is(notNullValue()));
        assertThat(buildingSnapshot.getName(), is(equalTo(buildingData.get("name"))));
        assertThat(buildingSnapshot.getAddress(), is(equalTo(buildingData.get("address"))));
        assertThat(buildingSnapshot.getCity(), is(equalTo(buildingData.get("city"))));
        return this;
    }

    public ThenBuildingBOTest exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        return this;
    }

    public void building_should_be_marked_as_usable() {
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot.isUsable(), is(true));
    }

    public void building_should_be_marked_as_not_usable() {
        BuildingSnapshot buildingSnapshot = buildingRepository.findOne(building.toSnapshot().getId()).toSnapshot();
        assertThat(buildingSnapshot.isUsable(), is(false));
    }

}
