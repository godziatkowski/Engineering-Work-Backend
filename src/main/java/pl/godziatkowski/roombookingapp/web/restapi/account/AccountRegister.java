package pl.godziatkowski.roombookingapp.web.restapi.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

import static pl.godziatkowski.roombookingapp.sharedkernel.constant.Constants.EMAIL_REGEXP;

public class AccountRegister {

    @NotNull
    @Size(min = 12, max = 255)
    @Pattern(regexp = EMAIL_REGEXP)
    private String login;

    @NotNull
    @Size(min = 1, max = 25)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;
    
    @ApiModelProperty(value = "Username for new account")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @ApiModelProperty(value = "First name of account owner")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ApiModelProperty(value = "Last name of account owner")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
