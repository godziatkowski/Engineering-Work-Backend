package pl.godziatkowski.roombookingapp.web.restapi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class AccountEditValidator
    extends AbstractValidator {

    private final IUserSnapshotFinder userSnapshotFinder;

    @Autowired
    public AccountEditValidator(IUserSnapshotFinder userSnapshotFinder) {
        this.userSnapshotFinder = userSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountEdit.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        AccountEdit accountEdit = (AccountEdit) target;

        if (loginIsTaken(accountEdit.getLogin())) {
            errors.rejectValue("login", "LoginAlreadyInUse");
        }
    }

    private boolean loginIsTaken(String login) {
        UserSnapshot userSnapshot = userSnapshotFinder.findOneByLoginIgnoreCase(login);
        return userSnapshot != null && !getLoggedUserSnapshot().getId().equals(userSnapshot.getId());
    }

    private UserSnapshot getLoggedUserSnapshot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userSnapshotFinder.findOneByLoginIgnoreCase(username);
    }
}
