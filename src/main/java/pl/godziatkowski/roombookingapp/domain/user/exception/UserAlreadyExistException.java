package pl.godziatkowski.roombookingapp.domain.user.exception;

public class UserAlreadyExistException
    extends RuntimeException {

    private static final long serialVersionUID = 6488770598759084283L;

    public UserAlreadyExistException() {
        super("User with given login already exist");
    }

    
    
}
