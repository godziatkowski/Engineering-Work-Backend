package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.List;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public interface IUserInitializer {

    List<UserSnapshot> initializeUsers();
}
