package pl.godziatkowski.roombookingapp.domain.user.bo;

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
import pl.godziatkowski.roombookingapp.domain.user.bo.steps.GivenUserBOTest;
import pl.godziatkowski.roombookingapp.domain.user.bo.steps.ThenUserBOTest;
import pl.godziatkowski.roombookingapp.domain.user.bo.steps.WhenUserBOTest;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;
import pl.godziatkowski.roombookingapp.service.IPasswordEncodingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserBOTest
    extends ScenarioTest<GivenUserBOTest, WhenUserBOTest, ThenUserBOTest> {

    public static String CLAZZ = UserBOTest.class.getSimpleName();

    @Autowired
    @ProvidedScenarioState
    private IUserRepository userRepository;
    @Autowired
    @ProvidedScenarioState
    private IPasswordEncodingService passwordEncodingService;
    @Autowired
    @ProvidedScenarioState
    private IUserBO userBO;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void should_register_new_user() {
        given().user_data(CLAZZ);
        when().register_invoked();
        then().user_should_be_registered();
    }

    @Test
    public void should_throw_user_already_exist_when_register_invoked_with_already_used_login() {
        given().user_data(CLAZZ).and().already_existing_user_with_given_data(CLAZZ);
        when().register_invoked();
        then().user_already_exist_exception_should_be_thrown();
    }

    @Test
    public void should_edit_already_existing_user() {
        given().already_existing_user_with_given_data(CLAZZ).and().user_data(CLAZZ + 1);
        when().edit_invoked();
        then().user_should_be_edited();
    }

    @Test
    public void should_throw_user_already_exist_when_trying_to_change_login_to_already_existing_one() {
        given().already_existing_user_with_given_data(CLAZZ + 1)
            .and().already_existing_user_with_given_data(CLAZZ)
            .and().user_data(CLAZZ + 1);
        when().edit_invoked();
        then().user_already_exist_exception_should_be_thrown();
    }

    @Test
    public void should_change_user_role() {
        given().already_existing_user_with_given_data(CLAZZ);
        when().grant_admin_rights_invoked();
        then().user_should_be_granted_Admin_role();
    }
    
    @Test
    public void should_cahange_user_password(){
        given().already_existing_user_with_given_data(CLAZZ).and().new_password_for_user("password");
        when().change_password_invoked();
        then().user_password_should_be_changed();
    }

}
