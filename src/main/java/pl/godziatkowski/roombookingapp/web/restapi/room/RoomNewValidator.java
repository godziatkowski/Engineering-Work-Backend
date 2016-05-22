package pl.godziatkowski.roombookingapp.web.restapi.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class RoomNewValidator
    extends AbstractValidator {

    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public RoomNewValidator(IRoomSnapshotFinder roomSnapshotFinder) {
        this.roomSnapshotFinder = roomSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomNew.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        RoomNew roomNew = (RoomNew) target;

        if (roomAlreadyExist(roomNew)) {
            errors.rejectValue("name", "RoomAlreadyExist");
        }

    }

    private boolean roomAlreadyExist(RoomNew roomNew) {
        return roomSnapshotFinder.findOneByName(roomNew.getName()) != null;
    }

}
