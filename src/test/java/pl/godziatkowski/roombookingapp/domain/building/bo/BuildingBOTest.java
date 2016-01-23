package pl.godziatkowski.roombookingapp.domain.building.bo;

import pl.godziatkowski.roombookingapp.domain.building.bo.IBuildingBO;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.godziatkowski.roombookingapp.Application;
import pl.godziatkowski.roombookingapp.domain.building.bo.steps.GivenBuildingBOTest;
import pl.godziatkowski.roombookingapp.domain.building.bo.steps.ThenBuildingBOTest;
import pl.godziatkowski.roombookingapp.domain.building.bo.steps.WhenBuildingBOTest;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BuildingBOTest
    extends ScenarioTest<GivenBuildingBOTest, WhenBuildingBOTest, ThenBuildingBOTest> {

    private static final String CLAZZ = BuildingBOTest.class.getSimpleName();

    @Autowired
    @ProvidedScenarioState
    private IBuildingRepository buildingRepository;
    @Autowired
    @ProvidedScenarioState
    private IBuildingBO buildingBO;

    public BuildingBOTest() {
    }

    @After
    public void tearDown() {
        buildingRepository.deleteAll();
    }

    @Test
    public void should_add_new_building() {
        given().building_data(CLAZZ).and().expectedBuildingsCount();
        when().add_invoked();
        then().should_create_building()
            .and().return_snapshot();
    }
    @Test
    public void should_throw_building_already_exists_exception_when_add_invoked_with_data_of_already_existing_building() {
        given().persisted_building(CLAZZ).and().building_data(CLAZZ).and().expectedBuildingsCount();
        when().add_invoked();
        then().exception_should_be_thrown();
    }

    @Test
    public void should_edit_building_data() {
        given().persisted_building(CLAZZ).and().building_data(CLAZZ + 1);
        when().edit_invoked();
        then().building_should_be_edited();
    }

    @Test
    public void should_throw_building_already_exists_exception_when_edit_invoked_with_data_of_other_building() {
        given().persisted_building(CLAZZ + 1).and().persisted_building(CLAZZ).and().building_data(CLAZZ + 1);
        when().edit_invoked();
        then().exception_should_be_thrown();
    }

    @Test
    public void should_mark_building_as_usable() {
        given().persisted_building(CLAZZ).and().building_is_not_usable();
        when().markAsUsable_invoked();
        then().building_should_be_marked_as_usable();
    }

    @Test
    public void should_mark_building_as_not_usable() {
        given().persisted_building(CLAZZ);
        when().markAsNotUsable_invoked();
        then().building_should_be_marked_as_not_usable();
    }

}
