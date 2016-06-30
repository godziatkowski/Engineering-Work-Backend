package pl.godziatkowski.roombookingapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.room.event.ReservationRejectedEvent;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.service.email.IReservationRejectedEmailService;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

@Component
public class ReservationRejectedEventListener
    implements ApplicationListener<ReservationRejectedEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRejectedEventListener.class);
    
    private final IReservationRejectedEmailService reservationRejectedEmailService;
    private final IUserSnapshotFinder userSnapshotFinder;
    private final int maximumCountOfFailureEmailSendingAttempts;

    @Autowired
    public ReservationRejectedEventListener(IReservationRejectedEmailService reservationRejectedEmailService,
        IUserSnapshotFinder userSnapshotFinder,
        @Value("${email.maximumCountOfFailureEmailSendingAttempts}") int maximumCountOfFailureEmailSendingAttempts) {
        this.reservationRejectedEmailService = reservationRejectedEmailService;
        this.userSnapshotFinder = userSnapshotFinder;
        this.maximumCountOfFailureEmailSendingAttempts = maximumCountOfFailureEmailSendingAttempts;
    }

    @Async
    @Override
    public void onApplicationEvent(ReservationRejectedEvent event) {
        UserSnapshot userSnapshot = userSnapshotFinder.findOneById(event.getReservationSnapshot().getUserId());
        int sendingAttempt = 0;
        while(sendingAttempt < maximumCountOfFailureEmailSendingAttempts){
            try {
                reservationRejectedEmailService.sendNotificationAboutRejectedReservation(userSnapshot, event.getReservationSnapshot());
                break;
            } catch (EmailSendingException e) {
                sendingAttempt++;
                if (sendingAttempt == maximumCountOfFailureEmailSendingAttempts) {
                    LOGGER.info("Cannot send notyfication about rejected reservation to <{}>", userSnapshot.getLogin());
                }
            }
        }

    }

}
