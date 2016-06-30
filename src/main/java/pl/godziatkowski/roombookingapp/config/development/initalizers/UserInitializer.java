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

        users.add(userBO.register("admin@cti.p.lodz.pl", "admin", "admin"));

        userBO.grantAdminRights(users.get(0).getId());

        users.add(userBO.register("180255@edu.p.lodz.pl", "Rafał", "Gorski"));
        users.add(userBO.register("BarbaraKaczmarek@kis.p.lodz.pl", "Barbara", "Kaczmarek"));
        users.add(userBO.register("TadeuszAdamski@cti.p.lodz.pl", "Tadeusz", "Adamski"));

        users.stream().forEach((user) -> {
            userBO.changePassword(user.getId(), "password");
        });

        return users;
    }

}
