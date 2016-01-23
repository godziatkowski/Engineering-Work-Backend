package pl.godziatkowski.roombookingapp.web.restapi.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;
import pl.godziatkowski.roombookingapp.domain.room.finder.IRoomSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class RoomNewValidator
    extends AbstractValidator {

    private final IRoomSnapshotFinder roomSnapshotFinder;

    @Autowired
    public RoomNewValidator(IRoomSnapshotFinder roomSnapshotFinder, IBuildingSnapshotFinder buildingSnapshotFinder) {
        this.roomSnapshotFinder = roomSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomNew.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        RoomNew roomNew = (RoomNew) target;

        if (roomAlreadyExistInBuilding(roomNew)) {
            errors.rejectValue("name", "RoomAlreadyExistInBuilding");
        }

    }

    private boolean roomAlreadyExistInBuilding(RoomNew roomNew) {
        return roomSnapshotFinder.findOneByNameAndBuildingId(roomNew.getName(), roomNew.getBuildingId()) != null;
    }

}
