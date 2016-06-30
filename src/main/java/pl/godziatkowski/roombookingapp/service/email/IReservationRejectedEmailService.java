package pl.godziatkowski.roombookingapp.service.email;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

public interface IReservationRejectedEmailService {

    void sendNotificationAboutRejectedReservation(UserSnapshot userSnapshot, ReservationSnapshot reservationSnapshot)
        throws EmailSendingException;
    
}
