package pl.godziatkowski.roombookingapp.domain.user.bo.steps;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;

public class GivenUserBOTest
    extends Stage<GivenUserBOTest> {

    @ExpectedScenarioState
    private IUserRepository userRepository;

    @ProvidedScenarioState
    private Map<String, String> userData = new HashMap<>();
    @ProvidedScenarioState
    private User user;
    @ProvidedScenarioState
    private Long alreadyExistingUserId;
    @ProvidedScenarioState
    private String password;

    public GivenUserBOTest user_data(String stringValue) {
        userData.put("login", stringValue);
        userData.put("password", stringValue);
        userData.put("firstName", stringValue);
        userData.put("lastName", stringValue);
        return this;
    }

    public GivenUserBOTest already_existing_user_with_given_data(String CLAZZ) {
        user = new User(CLAZZ, CLAZZ, CLAZZ, CLAZZ);
        user = userRepository.save(user);
        alreadyExistingUserId = user.toSnapshot().getId();
        return this;
    }

    public GivenUserBOTest new_password_for_user(String password) {
        this.password = password;
        return this;
    }

}
