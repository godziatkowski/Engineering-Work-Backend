package pl.godziatkowski.roombookingapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.user.event.NewUserRegisteredEvent;
import pl.godziatkowski.roombookingapp.service.email.INewUserRegisteredEmailService;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

@Component
public class NewUserRegisteredEventListener
    implements ApplicationListener<NewUserRegisteredEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NewUserRegisteredEventListener.class);
    
    private final INewUserRegisteredEmailService newUserRegisteredEmailService;
    private final int maximumCountOfFailureEmailSendingAttempts;

    @Autowired
    public NewUserRegisteredEventListener(INewUserRegisteredEmailService newUserRegisteredEmailService,
        @Value("${email.maximumCountOfFailureEmailSendingAttempts}") int maximumCountOfFailureEmailSendingAttempts) {
        this.newUserRegisteredEmailService = newUserRegisteredEmailService;
        this.maximumCountOfFailureEmailSendingAttempts = maximumCountOfFailureEmailSendingAttempts;
    }

    @Async
    @Override
    public void onApplicationEvent(NewUserRegisteredEvent event) {
        int sendingAttempt = 0;
        while(sendingAttempt < maximumCountOfFailureEmailSendingAttempts){
            try {
                newUserRegisteredEmailService.sendCredetialsToUser(event.getUserSnapshot(), event.getPassword());
                break;
            } catch (EmailSendingException e) {
                sendingAttempt++;
                if (sendingAttempt == maximumCountOfFailureEmailSendingAttempts) {
                    LOGGER.info("Cannot send credentials to <{}>", event.getUserSnapshot().getLogin());
                }
            }
        }

    }

}
