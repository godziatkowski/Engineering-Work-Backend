package pl.godziatkowski.roombookingapp.web.restapi.room;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class Keeper {

    private final Long id;
    private final String login;
    private final String firstName;
    private final String lastName;

    public Keeper(UserSnapshot userSnapshot) {
        this.id = userSnapshot.getId();
        this.login = userSnapshot.getLogin();
        this.firstName = userSnapshot.getFirstName();
        this.lastName = userSnapshot.getLastName();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
