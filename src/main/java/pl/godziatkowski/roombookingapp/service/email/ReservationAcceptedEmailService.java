package pl.godziatkowski.roombookingapp.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;

import static pl.godziatkowski.roombookingapp.sharedkernel.constant.Constants.*;

@Service
public class ReservationAcceptedEmailService
    implements IReservationAcceptedEmailService {

    private static final String EMAIL_TITLE = "Twoja rezerwacja została zaakceptowana";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationAcceptedEmailService.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final String sender;

    @Autowired
    public ReservationAcceptedEmailService(JavaMailSender mailSender,
        SpringTemplateEngine templateEngine,
        @Value("${email.sender}") String sender) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.sender = sender;
    }

    @Override
    public void sendNotificationAboutAcceptedReservation(UserSnapshot userSnapshot,
        ReservationSnapshot reservationSnapshot) throws EmailSendingException {

        LOGGER.info("Sending notification about accepted reservation for room <{}>at <{}> between <{}> - <{}> to <{}>",
            reservationSnapshot.getRoomSnapshot().getName(),
            reservationSnapshot.getStartDate().format(DATE),
            reservationSnapshot.getStartDate().format(TIME),
            reservationSnapshot.getEndDate().format(TIME),
            userSnapshot.getLogin()
        );

        try {
            sendEmail(userSnapshot, reservationSnapshot);
        } catch (MessagingException me) {
            LOGGER.error("Unable to send notice about accepted reservation because of MessagingException <{}>",
                me.getMessage(), me);
            throw new EmailSendingException();
        } catch (RuntimeException re) {
            LOGGER.error("Unable to send notice about accepted reservation because of RuntimeException <{}>",
                re.getMessage(), re);
            throw new EmailSendingException();
        }
    }

    private void sendEmail(UserSnapshot userSnapshot, ReservationSnapshot reservationSnapshot)
        throws MessagingException {
        final Context context = new Context();
        context.setVariable("user", userSnapshot.getLogin());
        context.setVariable("room", reservationSnapshot.getRoomSnapshot().getName());
        context.setVariable("date", reservationSnapshot.getStartDate().format(DATE));
        context.setVariable("start", reservationSnapshot.getStartDate().format(TIME));
        context.setVariable("end", reservationSnapshot.getEndDate().format(TIME));

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true = multipart
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(EMAIL_TITLE);
        message.setFrom(sender);
        message.setTo(userSnapshot.getLogin());

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("acceptedReservation", context);
        // true = isHtml
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);

        LOGGER.info("Sent notice about accepted reservation to <{}>", userSnapshot.getLogin());
    }

}
