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

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EmailSendingException;


@Service
public class NewUserRegisteredEmailService
    implements INewUserRegisteredEmailService {

    private static final String EMAIL_TITLE = "Otrzymałeś dostęp do aplikacji RoomBookingApp w Centrum Technologii Informatycznych Politechniki Łódzkiej";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationAcceptedEmailService.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final String sender;

    @Autowired
    public NewUserRegisteredEmailService(JavaMailSender mailSender,
        SpringTemplateEngine templateEngine,
        @Value("${email.sender}") String sender) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.sender = sender;
    }

    @Override
    public void sendCredetialsToUser(UserSnapshot userSnapshot,
        String password) throws EmailSendingException {

        LOGGER.info("Sending credentials for new user <{}>",
            userSnapshot.getLogin()
        );

        try {
            sendEmail(userSnapshot, password);
        } catch (MessagingException me) {
            LOGGER.error("Unable to send credentials for new user because of MessagingException <{}>",
                me.getMessage(), me);
            throw new EmailSendingException();
        } catch (RuntimeException re) {
            LOGGER.error("Unable to send credentials for new user because of RuntimeException <{}>",
                re.getMessage(), re);
            throw new EmailSendingException();
        }
    }

    private void sendEmail(UserSnapshot userSnapshot, String password)
        throws MessagingException {
        final Context context = new Context();
        context.setVariable("user", userSnapshot.getLogin());
        context.setVariable("password", password);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        // true = multipart
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(EMAIL_TITLE);
        message.setFrom(sender);
        message.setTo(userSnapshot.getLogin());

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("newUserRegistered", context);
        // true = isHtml
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);

        LOGGER.info("Sent credentials for new user to <{}>", userSnapshot.getLogin());
    }

}
