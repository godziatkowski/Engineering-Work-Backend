package pl.godziatkowski.roombookingapp.config.development;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.config.development.initalizers.IReservationInitializer;
import pl.godziatkowski.roombookingapp.config.development.initalizers.IRoomInitlializer;
import pl.godziatkowski.roombookingapp.config.development.initalizers.IUserInitializer;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Component
@Profile(Profiles.DEVELOPMENT)
public class DataInitializer {

    @Autowired
    private IRoomSnapshotFinder roomSnapshotFinder;
    @Autowired
    private IRoomInitlializer roomInitializer;
    @Autowired
    private IReservationInitializer reservationInitializer;

    @Autowired
    private IUserSnapshotFinder userSnapshotFinder;
    @Autowired
    private IUserInitializer userInitializer;

    @Profile(Profiles.DEVELOPMENT)
    @PostConstruct
    public void init() {

        List<UserSnapshot> userSnapshots = userSnapshotFinder.findAll();
        if (userSnapshots.isEmpty() || userSnapshots.size() == 1) {
            userSnapshots.addAll(userInitializer.initializeUsers());
        }

        if (roomSnapshotFinder.findAll().isEmpty()) {
            List<Long> roomIds = roomInitializer.initializeRooms();
            reservationInitializer.initializeReservations(roomIds, userSnapshots);
        }

    }
}
