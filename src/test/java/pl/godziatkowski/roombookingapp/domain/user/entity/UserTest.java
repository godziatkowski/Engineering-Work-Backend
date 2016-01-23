package pl.godziatkowski.roombookingapp.domain.user.entity;

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
import pl.godziatkowski.roombookingapp.domain.user.entity.steps.GivenUserTest;
import pl.godziatkowski.roombookingapp.domain.user.entity.steps.ThenUserTest;
import pl.godziatkowski.roombookingapp.domain.user.entity.steps.WhenUserTest;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserTest
    extends ScenarioTest<GivenUserTest, WhenUserTest, ThenUserTest> {

    private static final String CLAZZ = "UserTest";

    @Autowired
    @ProvidedScenarioState
    private IUserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void should_change_user_role() {
        given().a_persisted_user(CLAZZ);
        when().grant_admin_right_invoked();
        then().user_should_be_granted_admin_role();
    }

    @Test
    public void should_edit_user() {
        given().a_persisted_user(CLAZZ).and().new_user_data();
        when().edit_is_invoked();
        then().user_data_should_be_edited();
        
    }
    
    @Test
    public void should_change_user_password(){
        given().a_not_persisted_user(CLAZZ).and().new_password_for_user("password");
        when().change_password_invoked();
        then().user_password_should_be_changed();
    }

    @Test
    public void should_return_snapshot_when_toSnapshot_invoked() {
        given().a_persisted_user(CLAZZ);
        when().toSnapshot_invoked();
        then().snapshot_should_be_returned();
    }

    @Test
    public void should_throw_exception_when_toSnapshot_invkoed_on_not_persisted_user() {
        given().a_not_persisted_user(CLAZZ);
        when().toSnapshot_invoked();
        then().entity_in_state_new_exception_should_be_thrown();
    }

}
