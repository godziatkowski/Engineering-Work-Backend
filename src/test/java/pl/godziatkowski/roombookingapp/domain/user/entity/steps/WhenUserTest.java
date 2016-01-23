package pl.godziatkowski.roombookingapp.domain.user.entity.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

public class WhenUserTest
    extends Stage<WhenUserTest> {

    @ExpectedScenarioState
    private User user;
    @ExpectedScenarioState
    private String password;

    @ExpectedScenarioState
    private Map<String, String> userData;
    @ProvidedScenarioState
    private UserSnapshot userSnapshot;
    @ProvidedScenarioState
    private Boolean exceptionThrown;

    public void grant_admin_right_invoked() {
        user.grantAdminRights();
    }

    public void edit_is_invoked() {
        user.edit(userData.get("login"), userData.get("firstName"), userData.get("lastName"));
    }

    public void toSnapshot_invoked() {
        exceptionThrown = false;
        try{
        userSnapshot = user.toSnapshot();
        }catch(EntityInStateNewException ex){
            exceptionThrown = true;
        }
    }

    public void change_password_invoked() {
        user.changePassword(password);
    }

}
