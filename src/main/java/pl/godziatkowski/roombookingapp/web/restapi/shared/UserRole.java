package pl.godziatkowski.roombookingapp.web.restapi.shared;

public enum UserRole {

    ADMIN,
    KEEPER,
    USER;

    public static pl.godziatkowski.roombookingapp.domain.user.entity.UserRole convertToDomainValue(UserRole restapiValue) {
        pl.godziatkowski.roombookingapp.domain.user.entity.UserRole userRole = null;
        switch (restapiValue) {
            case USER:
                userRole = pl.godziatkowski.roombookingapp.domain.user.entity.UserRole.USER;
                break;
            case ADMIN:
                userRole = pl.godziatkowski.roombookingapp.domain.user.entity.UserRole.ADMIN;
                break;
            case KEEPER:
                userRole = pl.godziatkowski.roombookingapp.domain.user.entity.UserRole.KEEPER;
                break;
            default:
                throw new AssertionError();
        }
        return userRole;
    }

}
