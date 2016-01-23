package pl.godziatkowski.roombookingapp.domain.building.entity;

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
import pl.godziatkowski.roombookingapp.domain.building.entity.steps.GivenBuildingTest;
import pl.godziatkowski.roombookingapp.domain.building.entity.steps.ThenBuildingTest;
import pl.godziatkowski.roombookingapp.domain.building.entity.steps.WhenBuildingTest;
import pl.godziatkowski.roombookingapp.domain.building.repository.IBuildingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BuildingTest
    extends ScenarioTest<GivenBuildingTest, WhenBuildingTest, ThenBuildingTest> {

    private static final String CLAZZ = BuildingTest.class.getSimpleName();

    @Autowired
    @ProvidedScenarioState
    private IBuildingRepository buildingRepository;

    @After
    public void tearDown() {
        buildingRepository.deleteAll();
    }

    @Test
    public void should_edit_building_data() {
        given().an_existing_building(CLAZZ).and().a_building_data(CLAZZ+1);
        when().edit_invoked();
        then().buildin_should_be_edited();
    }

    @Test
    public void should_mark_building_as_usable() {
        given().an_existing_building(CLAZZ).and().given_building_is_not_usable();
        when().mark_as_usable_invoked();
        then().building_should_be_marked_as_usable();
    }

    @Test
    public void should_mark_building_as_not_usable() {
        given().an_existing_building(CLAZZ);
        when().mark_as_not_usabl_invoked();
        then().building_should_be_marked_as_not_usable();
    }

    @Test
    public void should_return_snapshot() {
        given().an_existing_building(CLAZZ);
        when().toSnapshot_invoked();
        then().snapshot_should_be_returned();
    }
    @Test
    public void should_throw_entity_in_state_new_exception() {
        given().not_persisted_building(CLAZZ);
        when().toSnapshot_invoked();
        then().exception_should_be_thrown();
    }

}
