package pl.godziatkowski.roombookingapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.room.event.ReservationAcceptedEvent;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.service.email.IReservationAcceptedEmailService;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

@Component
public class ReservationAcceptedEventListener
    implements ApplicationListener<ReservationAcceptedEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationAcceptedEventListener.class);
    
    private final IReservationAcceptedEmailService reservationAcceptedEmailService;
    private final IUserSnapshotFinder userSnapshotFinder;
    private final int maximumCountOfFailureEmailSendingAttempts;

    @Autowired
    public ReservationAcceptedEventListener(IReservationAcceptedEmailService reservationAcceptedEmailService,
        IUserSnapshotFinder userSnapshotFinder,
        @Value("${email.maximumCountOfFailureEmailSendingAttempts}") int maximumCountOfFailureEmailSendingAttempts) {
        this.reservationAcceptedEmailService = reservationAcceptedEmailService;
        this.userSnapshotFinder = userSnapshotFinder;
        this.maximumCountOfFailureEmailSendingAttempts = maximumCountOfFailureEmailSendingAttempts;
    }

    @Async
    @Override
    public void onApplicationEvent(ReservationAcceptedEvent event) {
        UserSnapshot userSnapshot = userSnapshotFinder.findOneById(event.getReservationSnapshot().getUserId());
        int sendingAttempt = 0;
        while(sendingAttempt < maximumCountOfFailureEmailSendingAttempts){
            try {
                reservationAcceptedEmailService.sendNotificationAboutAcceptedReservation(userSnapshot, event.getReservationSnapshot());
                break;
            } catch (EmailSendingException e) {
                sendingAttempt++;
                if (sendingAttempt == maximumCountOfFailureEmailSendingAttempts) {
                    LOGGER.info("Cannot send notyfication about accpeted reservation to <{}>", userSnapshot.getLogin());
                }
            }
        }

    }

}
