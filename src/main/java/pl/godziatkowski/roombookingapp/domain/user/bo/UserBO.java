package pl.godziatkowski.roombookingapp.domain.user.bo;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.event.NewUserRegisteredEvent;
import pl.godziatkowski.roombookingapp.domain.user.exception.UserAlreadyExistException;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;
import pl.godziatkowski.roombookingapp.service.IPasswordEncodingService;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.BusinessObject;

@BusinessObject
public class UserBO
    implements IUserBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBO.class);

    private final IUserRepository userRepository;
    private final IPasswordEncodingService passwordEncodingService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserBO(IUserRepository userRepository, IPasswordEncodingService passwordEncodingService,
        ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncodingService = passwordEncodingService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserSnapshot register(String login, String firstName, String lastName) {
        String password = UUID.randomUUID().toString().substring(0, 8);
        if (userRepository.findOneByLoginIgnoreCase(login) != null) {
            throw new UserAlreadyExistException();
        }
        User user = new User(login, passwordEncodingService.encode(password), firstName, lastName);
        user = userRepository.save(user);

        UserSnapshot userSnapshot = user.toSnapshot();

        LOGGER.info("Created user account with id: <{}> and login: <{}> for user <{}> <{}>",
            userSnapshot.getId(), userSnapshot.getLogin(), userSnapshot.getFirstName(), userSnapshot.getLastName());

        NewUserRegisteredEvent event = new NewUserRegisteredEvent(this, userSnapshot, password);

        eventPublisher.publishEvent(event);
        return userSnapshot;
    }

    @Override
    public void edit(Long id, String login, String firstName, String lastName) {
        User user = userRepository.findOneByLoginIgnoreCase(login);
        if (user != null) {
            if (!user.toSnapshot().getId().equals(id)) {
                throw new UserAlreadyExistException();
            }
        } else {
            user = userRepository.findOne(id);
        }
        user.edit(login, firstName, lastName);
        userRepository.save(user);
        LOGGER.info("Changed data of user with id <{}>. Login: <{}>, first name: <{}>, last name: <{}>",
            id, login, firstName, lastName);
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.changePassword(passwordEncodingService.encode(password));
            LOGGER.info("Password changed for user with id <{}>", id);
        }
    }

    @Override
    public void grantAdminRights(Long id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.grantAdminRights();
            LOGGER.info("Admin rights granted for user with id <{}>", id);
        }
    }

}
