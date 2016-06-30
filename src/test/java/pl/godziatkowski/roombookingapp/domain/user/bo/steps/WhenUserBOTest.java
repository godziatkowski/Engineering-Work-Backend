package pl.godziatkowski.roombookingapp.domain.user.bo.steps;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.exception.UserAlreadyExistException;

public class WhenUserBOTest
    extends Stage<WhenUserBOTest> {

    @ExpectedScenarioState
    private IUserBO userBO;
    @ExpectedScenarioState
    private Map<String, String> userData;
    @ExpectedScenarioState
    private Long alreadyExistingUserId;
    @ExpectedScenarioState
    private String password;

    @ProvidedScenarioState
    private Boolean exceptionWasThrown;
    @ProvidedScenarioState
    private UserSnapshot userSnapshot;

    public void register_invoked() {
        exceptionWasThrown = false;
        try {
            userSnapshot = userBO.register(
                userData.get("login"),
                userData.get("firstName"),
                userData.get("lastName")
            );
        } catch (UserAlreadyExistException exception) {
            exceptionWasThrown = true;
        }
    }

    public void edit_invoked() {
        exceptionWasThrown = false;
        try {
            userBO.edit(
                alreadyExistingUserId,
                userData.get("login"),
                userData.get("firstName"),
                userData.get("lastName"));
        } catch (Exception exception) {
            exceptionWasThrown = true;
        }

    }

    public void grant_admin_rights_invoked() {
        userBO.grantAdminRights(alreadyExistingUserId);
    }

    public void change_password_invoked() {
        userBO.changePassword(alreadyExistingUserId, password);
    }

}
