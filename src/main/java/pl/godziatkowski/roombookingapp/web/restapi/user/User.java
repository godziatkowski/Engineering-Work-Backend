package pl.godziatkowski.roombookingapp.web.restapi.user;

import pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

@ApiModel
public class User {

    private final Long id;
    private final String login;
    private final String lastName;
    private final String firstName;
    private final Set<UserRole> userRoles;

    public User(UserSnapshot userSnapshot) {
        this.id = userSnapshot.getId();
        this.login = userSnapshot.getLogin();
        this.firstName = userSnapshot.getFirstName();
        this.lastName = userSnapshot.getLastName();
        this.userRoles = userSnapshot.getUserRoles()
            .stream()
            .map(userRole -> {
                return pl.godziatkowski.roombookingapp.domain.user.entity.UserRole.convertToRestapiValue(userRole);
            })
            .collect(Collectors.toSet());
    }

    @ApiModelProperty(value = "User unique identifier")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "User login")
    public String getLogin() {
        return login;
    }

    @ApiModelProperty(value = "User first name")
    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(value = "User last name")
    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(value = "Granted roles for user")
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

}
