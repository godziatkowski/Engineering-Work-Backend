package pl.godziatkowski.roombookingapp.domain.user.bo.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.entity.UserRole;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;
import pl.godziatkowski.roombookingapp.service.IPasswordEncodingService;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenUserBOTest
    extends Stage<ThenUserBOTest> {

    @ExpectedScenarioState
    private IPasswordEncodingService passwordEncodingService;
    @ExpectedScenarioState
    private Map<String, String> userData;
    @ExpectedScenarioState
    private UserSnapshot userSnapshot;
    @ExpectedScenarioState
    private Boolean exceptionWasThrown;
    @ExpectedScenarioState
    private Long alreadyExistingUserId;
    @ExpectedScenarioState
    private IUserRepository userRepository;
    @ExpectedScenarioState
    private String password;

    public ThenUserBOTest user_should_be_registered() {
        assertThat(exceptionWasThrown, is(false));
        assertThat(userSnapshot, is(notNullValue()));
        assertThat(userSnapshot.getLogin(), is(equalTo(userData.get("login"))));
        assertThat(userSnapshot.getFirstName(), is(equalTo(userData.get("firstName"))));
        assertThat(userSnapshot.getLastName(), is(equalTo(userData.get("lastName"))));
        return this;
    }

    public ThenUserBOTest user_should_be_edited() {
        UserSnapshot userSnapshot = userRepository.findOne(alreadyExistingUserId).toSnapshot();
        assertThat(userSnapshot, is(notNullValue()));
        assertThat(userSnapshot.getLogin(), is(equalTo(userData.get("login"))));        
        assertThat(userSnapshot.getFirstName(), is(equalTo(userData.get("firstName"))));
        assertThat(userSnapshot.getLastName(), is(equalTo(userData.get("lastName"))));
        return this;
    }

    public ThenUserBOTest user_already_exist_exception_should_be_thrown() {
        assertThat(exceptionWasThrown, is(true));
        return this;
    }

    public ThenUserBOTest user_should_be_granted_Admin_role() {
        UserSnapshot userSnapshot = userRepository.findOne(alreadyExistingUserId).toSnapshot();
        assertThat(userSnapshot.getUserRoles().contains(UserRole.ADMIN), is(true));
        return this;
    }

    public ThenUserBOTest user_password_should_be_changed() {
        UserSnapshot userSnapshot = userRepository.findOne(alreadyExistingUserId).toSnapshot();
        assertThat(passwordEncodingService.isMatch(password, userSnapshot.getPassword()), is(true));
        return this;
    }

}
