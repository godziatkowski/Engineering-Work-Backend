package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Service
@Profile(Profiles.DEVELOPMENT)
public class UserInitializer
    implements IUserInitializer {

    private final IUserBO userBO;

    @Autowired
    public UserInitializer(IUserBO userBO) {
        this.userBO = userBO;
    }

    @Override
    public List<UserSnapshot> initializeUsers() {
        List<UserSnapshot> users = new ArrayList<>();

        users.add(userBO.register("admin", "admin", "admin", "admin"));

        userBO.grantAdminRights(users.get(0).getId());

        users.add(userBO.register("RafalGorski", "qwert", "Rafał", "Gorski"));
        users.add(userBO.register("BarbaraKaczmarek", "trewq", "Barbara", "Kaczmarek"));
        users.add(userBO.register("TadeuszAdamski", "zaqwsx", "Tadeusz", "Adamski"));

        return users;
    }

}
