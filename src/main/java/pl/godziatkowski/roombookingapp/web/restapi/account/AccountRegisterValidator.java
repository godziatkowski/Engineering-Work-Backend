package pl.godziatkowski.roombookingapp.web.restapi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;



@RestValidator
public class AccountRegisterValidator extends AbstractValidator {

	private final IUserSnapshotFinder userSnapshotFinder;

	@Autowired
	public AccountRegisterValidator(IUserSnapshotFinder userSnapshotFinder) {
		this.userSnapshotFinder = userSnapshotFinder;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AccountRegister.class.isAssignableFrom(clazz);
	}

	@Override
	public void customValidation(Object target, Errors errors) {
		AccountRegister accountRegister = (AccountRegister) target;
		
		if (loginIsTaken(accountRegister.getLogin())) {
			errors.rejectValue("login", "LoginAlreadyInUse");
		}
	}

	private boolean loginIsTaken(String login) {
		return userSnapshotFinder.findOneByLoginIgnoreCase(login) != null;
	}
}
