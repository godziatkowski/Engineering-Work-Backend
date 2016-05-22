package pl.godziatkowski.roombookingapp.web.restapi.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class RoomEditValidator
    extends AbstractValidator {

    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public RoomEditValidator(IRoomSnapshotFinder roomSnapshotFinder) {
        this.roomSnapshotFinder = roomSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomEdit.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        RoomEdit roomNew = (RoomEdit) target;
        RoomSnapshot roomSnapshot = findRoom(roomNew);
        if (otherRoomWithGivenDataExist(roomSnapshot, roomNew)) {
            errors.rejectValue("name", "RoomAlreadyExist");
        }

    }

    private RoomSnapshot findRoom(RoomEdit roomNew) {
        RoomSnapshot roomSnapshot
            = roomSnapshotFinder.findOneByName(roomNew.getName());
        return roomSnapshot;
    }

    private boolean otherRoomWithGivenDataExist(RoomSnapshot roomSnapshot, RoomEdit roomNew) {
        return roomSnapshot != null && !roomSnapshot.getId().equals(roomNew.getId());
    }

}
