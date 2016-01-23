package pl.godziatkowski.roombookingapp.domain.user.entity.steps;

import java.util.HashMap;
import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;

public class GivenUserTest
    extends Stage<GivenUserTest> {

    private static final String CLAZZ = "GivenUserTest";

    @ExpectedScenarioState
    private IUserRepository userRepository;

    @ProvidedScenarioState
    private User user;
    @ProvidedScenarioState
    private Map<String, String> userData = new HashMap<>();
    @ProvidedScenarioState
    private String password;

    private User createUser(String stringValue) {
        return new User(stringValue, stringValue, stringValue, stringValue);
    }

    public GivenUserTest a_persisted_user(String stringValue) {
        user = createUser(stringValue);
        user = userRepository.save(user);
        putDataInMap(stringValue);
        return this;
    }

    public GivenUserTest new_user_data() {
        
        putDataInMap(CLAZZ);

        return this;
    }

    private GivenUserTest putDataInMap(String stringData) {
        userData.put("login", stringData);
        userData.put("password", stringData);
        userData.put("firstName", stringData);
        userData.put("lastName", stringData);
        return this;
    }

    public GivenUserTest a_not_persisted_user(String stringValue) {
        user = createUser(stringValue);
        return this;
    }

    public GivenUserTest new_password_for_user(String password) {
        this.password = password;
        return this;
    }

}
