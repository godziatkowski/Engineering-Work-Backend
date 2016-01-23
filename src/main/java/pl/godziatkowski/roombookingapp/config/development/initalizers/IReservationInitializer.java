package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.List;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public interface IReservationInitializer {

    void initializeReservations(List<Long> roomIds, List<UserSnapshot> userSnapshots);

}
