package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import pl.godziatkowski.roombookingapp.domain.room.bo.IReservationBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Service
@Profile(Profiles.DEVELOPMENT)
public class ReservationInitializer
    implements IReservationInitializer {

    private final IReservationBO reservationBO;

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    public ReservationInitializer(IReservationBO reservationBO) {
        this.reservationBO = reservationBO;
    }

    @Override
    public void initializeReservations(List<Long> roomIds, List<UserSnapshot> userSnapshots) {
        for (int week = 0; week < 5; week++) {
            for (int i = 0; i < userSnapshots.size(); i++) {
                if (roomIds.get(i) != null) {
                    reservationBO.reserveRoom(roomIds.get(i), userSnapshots.get(i).getId(), NOW.plusWeeks(week),
                        NOW.plusWeeks(week).plusHours(1+i));
                } else {
                    break;
                }
            }
        }

    }

}
