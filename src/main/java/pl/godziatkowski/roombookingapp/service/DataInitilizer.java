package pl.godziatkowski.roombookingapp.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Component
@Profile({Profiles.DEVELOPMENT, Profiles.PRODUCTION})
public class DataInitilizer {

    @Autowired
    private IAdminInitializer adminInitlizer;
    @Autowired
    private IUserSnapshotFinder userSnapshotFider;

    @PostConstruct
    public void init() {

        if (userSnapshotFider.findCountOfUsersWithRoleAdmin().equals(0)) {
            adminInitlizer.initialize();
        }

    }
}
