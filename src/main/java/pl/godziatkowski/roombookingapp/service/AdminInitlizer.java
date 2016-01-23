package pl.godziatkowski.roombookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Component
@Transactional
@Profile({Profiles.DEVELOPMENT, Profiles.PRODUCTION})
public class AdminInitlizer
    implements IAdminInitializer {

    @Autowired
    @Value("${data.initialize.user.defaultAdmin.login}")
    private String login;

    @Autowired
    @Value("${data.initialize.user.defaultAdmin.password}")
    private String password;

    @Autowired
    @Value("${project.name}")
    private String name;
    
    @Autowired
    private IUserBO userBO;

    @Override
    public void initialize() {
        UserSnapshot userSnapshot = userBO.register(login, password, name, name);
        userBO.grantAdminRights(userSnapshot.getId());
    }

}
