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
        List<UserSnapshot> userIds = new ArrayList<>();

        userIds.add(userBO.register("RafałGorski", "qwert", "Rafał", "Gorski"));
        userIds.add(userBO.register("BarbaraKaczmarek", "trewq", "Barbara", "Kaczmarek"));
        userIds.add(userBO.register("TadeuszAdamski", "zaqwsx", "Tadeusz", "Adamski"));

        return userIds;
    }

}
