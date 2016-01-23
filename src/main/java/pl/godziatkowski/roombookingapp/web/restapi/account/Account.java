package pl.godziatkowski.roombookingapp.web.restapi.account;

import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole;

@ApiModel
public class Account {

    private final Long id;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final Set<Long> watchedRoomsIds;
    private final Set<UserRole> userRoles;

    public Account(UserSnapshot userSnapshot) {
        this.id = userSnapshot.getId();
        this.login = userSnapshot.getLogin();
        this.firstName = userSnapshot.getFirstName();
        this.lastName = userSnapshot.getLastName();
        this.watchedRoomsIds = userSnapshot.getWatchedRoomsIds();
        this.userRoles = userSnapshot.getUserRoles()
            .stream()
            .map(userRole -> {
                return pl.godziatkowski.roombookingapp.domain.user.entity.UserRole.convertToRestapiValue(userRole);
            })
            .collect(Collectors.toSet());
    }

    @ApiModelProperty(value = "Account unique identifier")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Username for account")
    public String getLogin() {
        return login;
    }

    @ApiModelProperty(value = "First name of account owner")
    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(value = "Last name of account owner")
    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(value = "Ids of rooms that account owner is watching")
    public Set<Long> getWatchedRoomsIds() {
        return watchedRoomsIds;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

}
