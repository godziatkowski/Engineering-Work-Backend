package pl.godziatkowski.roombookingapp.domain.user.event;

import org.springframework.context.ApplicationEvent;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class NewUserRegisteredEvent
    extends ApplicationEvent {

    private static final long serialVersionUID = -6700585482096511511L;

    private final UserSnapshot userSnapshot;
    private final String password;

    public NewUserRegisteredEvent(Object source, UserSnapshot userSnapshot, String password) {
        super(source);
        this.userSnapshot = userSnapshot;
        this.password = password;
    }

    public UserSnapshot getUserSnapshot() {
        return userSnapshot;
    }

    public String getPassword() {
        return password;
    }

}
