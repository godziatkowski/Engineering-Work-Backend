package pl.godziatkowski.roombookingapp.domain.user.dto;

import java.util.Set;

import pl.godziatkowski.roombookingapp.domain.user.entity.UserRole;

public class UserSnapshot {

    private final Long id;
    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Set<UserRole> userRoles;
    private final Set<Long> watchedRooms;

    public UserSnapshot(Long id, String login, String password, String firstName, String lastName,
        Set<UserRole> userRoles, Set<Long> watchedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoles = userRoles;
        this.watchedRooms = watchedRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public Set<Long> getWatchedRoomsIds() {
        return watchedRooms;
    }

}
