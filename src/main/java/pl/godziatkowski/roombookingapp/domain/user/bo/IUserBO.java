package pl.godziatkowski.roombookingapp.domain.user.bo;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public interface IUserBO {

    UserSnapshot register(String login, String password, String firstName, String lastName);

    void edit(Long id, String login, String firstName, String lastName);

    void changePassword(Long id, String password);
    
    void grantAdminRights(Long id);

}
