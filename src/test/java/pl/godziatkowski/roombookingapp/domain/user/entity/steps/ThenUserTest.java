package pl.godziatkowski.roombookingapp.domain.user.entity.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.entity.UserRole;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ThenUserTest
    extends Stage<ThenUserTest> {

    @ExpectedScenarioState
    private IUserRepository userRepository;
    @ExpectedScenarioState
    private User user;
    @ExpectedScenarioState
    private Map<String, String> userData;
    @ExpectedScenarioState
    private UserSnapshot userSnapshot;
    @ExpectedScenarioState
    private Boolean exceptionThrown;
    @ExpectedScenarioState
    private String password;

    private UserSnapshot castUserToSnapshot() {
        return userRepository.save(user).toSnapshot();
    }

    public void user_should_be_granted_admin_role() {
        UserSnapshot userSnapshot = castUserToSnapshot();
        assertThat(userSnapshot.getUserRoles().contains(UserRole.ADMIN), is(true));
    }

    public void user_data_should_be_edited() {
        UserSnapshot userSnapshot = castUserToSnapshot();
        assertThat(userSnapshot.getLogin(), is(equalTo(userData.get("login"))));
        assertThat(userSnapshot.getFirstName(), is(equalTo(userData.get("firstName"))));
        assertThat(userSnapshot.getLastName(), is(equalTo(userData.get("lastName"))));
    }

    public void snapshot_should_be_returned() {
        assertThat(exceptionThrown, is(false));
        assertThat(userSnapshot.getLogin(), is(equalTo(userData.get("login"))));
        assertThat(userSnapshot.getFirstName(), is(equalTo(userData.get("firstName"))));
        assertThat(userSnapshot.getLastName(), is(equalTo(userData.get("lastName"))));
        assertThat(userSnapshot.getPassword(), is(equalTo(userData.get("password"))));
    }

    public void entity_in_state_new_exception_should_be_thrown() {
        assertThat(exceptionThrown, is(true));
    }

    public void user_password_should_be_changed() {
        UserSnapshot userSnapshot = castUserToSnapshot();
        assertThat(userSnapshot.getPassword(), is(equalTo(password)));
    }

}
