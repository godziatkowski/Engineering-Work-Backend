package pl.godziatkowski.roombookingapp.domain.user.bo;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public interface IUserBO {

    UserSnapshot register(String login, String firstName, String lastName);

    void edit(Long id, String login, String firstName, String lastName);

    void changePassword(Long id, String password);

    void grantAdminRights(Long id);

    void addWatchedRoom(long id, long roomId);

    void removeWatchedRoom(long id, long roomId);

}
