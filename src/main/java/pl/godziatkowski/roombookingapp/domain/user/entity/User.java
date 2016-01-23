package pl.godziatkowski.roombookingapp.domain.user.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

@Entity
public class User
    implements Serializable {

    private static final long serialVersionUID = -691308294194502785L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(length = 20, unique = true, nullable = false)
    private String login;

    @NotNull
    @Size(min = 4, max = 60)
    @Column(length = 60)
    private String password;

    @NotNull
    @Size(min = 1, max = 25)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> watchedRooms = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<UserRole> userRoles = new HashSet<>();

    protected User() {
    }

    public User(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;        
        this.password = password;
        this.userRoles.add(UserRole.USER);
    }

    public void grantAdminRights() {
        userRoles.add(UserRole.ADMIN);
    }

    public void edit(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void changePassword( String password){
        this.password = password;
    }

    public void startWatchingRoom(Long roomId) {
        watchedRooms.add(id);
    }

    public void stopWatchingRoom(Long roomId) {
        watchedRooms.remove(id);
    }

    public UserSnapshot toSnapshot() {
        if (id == null) {
            throw new EntityInStateNewException();
        }
        return new UserSnapshot(id, login, password, firstName, lastName, userRoles, watchedRooms);
    }

}
