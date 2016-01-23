package pl.godziatkowski.roombookingapp.domain.user.entity;

public enum UserRole {

    USER,
    ADMIN;

    public static pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole convertToRestapiValue(UserRole domainValue) {
        pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole userRole = null;
        switch (domainValue) {
            case USER:
                userRole = pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole.USER;
                break;
            case ADMIN:
                userRole = pl.godziatkowski.roombookingapp.web.restapi.shared.UserRole.ADMIN;
                break;
            default:
                throw new AssertionError();
        }
        return userRole;
    }

}
