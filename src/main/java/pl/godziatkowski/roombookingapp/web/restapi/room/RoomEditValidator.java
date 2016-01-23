package pl.godziatkowski.roombookingapp.web.restapi.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class RoomEditValidator
    extends AbstractValidator {

    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public RoomEditValidator(IRoomSnapshotFinder roomSnapshotFinder, IBuildingSnapshotFinder buildingSnapshotFinder) {
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
        if (otherRoomWithGivenDataExistInBuilding(roomSnapshot, roomNew)) {
            errors.rejectValue("name", "RoomAlreadyExistInBuilding");
        }

    }

    private RoomSnapshot findRoom(RoomEdit roomNew) {
        RoomSnapshot roomSnapshot
            = roomSnapshotFinder.findOneByNameAndBuildingId(roomNew.getName(), roomNew.getBuildingId());
        return roomSnapshot;
    }

    private boolean otherRoomWithGivenDataExistInBuilding(RoomSnapshot roomSnapshot, RoomEdit roomNew) {
        return roomSnapshot != null && !roomSnapshot.getId().equals(roomNew.getId());
    }

}
