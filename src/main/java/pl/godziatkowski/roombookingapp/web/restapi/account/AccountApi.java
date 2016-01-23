package pl.godziatkowski.roombookingapp.web.restapi.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final IUserSnapshotFinder userSnapshotFinder;
    private final IUserBO userBO;
    private final Validator userRegisterValidator;
    private final Validator userEditValidator;

    @Autowired
    public AccountApi(IUserSnapshotFinder userSnapshotFinder, IUserBO userBO,
        @Qualifier("accountRegisterValidator") Validator userRegisterValidator,
        @Qualifier("accountEditValidator") Validator userEditValidator) {
        this.userSnapshotFinder = userSnapshotFinder;
        this.userBO = userBO;
        this.userRegisterValidator = userRegisterValidator;
        this.userEditValidator = userEditValidator;
    }

    @InitBinder("accountRegister")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(userRegisterValidator);
    }

    @InitBinder("accountEdit")
    protected void initEditBinder(WebDataBinder binder) {
        binder.setValidator(userEditValidator);
    }

    private UserSnapshot getLoggedUserSnapshot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userSnapshotFinder.findOneByLoginIgnoreCase(username);
    }

    @ApiOperation(value = "Create new account", notes = "Returns login and id of created account")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Account created")})
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Account> register(@Valid @RequestBody AccountRegister accountNew) {
        UserSnapshot userSnapshot = userBO.register(accountNew.getLogin(), accountNew.getPassword(),
            accountNew.getFirstName(), accountNew.getLastName());

        return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
    }

    @ApiOperation(value = "Get account of logged user", notes = "Returns account of logged user")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Found account of logged user")})
    @RequestMapping(method = GET)
    public HttpEntity<Account> get() {
        UserSnapshot userSnapshot = getLoggedUserSnapshot();
        return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
    }

    @ApiOperation(value = "Change account data for currently logged user", notes = "Empty body")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Account data edited")})
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Account> edit(@Valid @RequestBody AccountEdit accountEdit) {
        UserSnapshot userSnapshot = getLoggedUserSnapshot();

        userBO.edit(userSnapshot.getId(), accountEdit.getLogin(), accountEdit.getFirstName(),
            accountEdit.getLastName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public HttpEntity<Account> changePassword(@RequestBody String password) {
        if(password.length() < 4){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        UserSnapshot userSnapshot = getLoggedUserSnapshot();        
        userBO.changePassword(userSnapshot.getId(), password);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
