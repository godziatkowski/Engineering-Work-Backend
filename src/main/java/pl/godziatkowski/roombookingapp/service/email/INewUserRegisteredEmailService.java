package pl.godziatkowski.roombookingapp.service.email;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

public interface INewUserRegisteredEmailService {

    void sendCredetialsToUser(UserSnapshot userSnapshot, String password)
        throws EmailSendingException;
    
}
