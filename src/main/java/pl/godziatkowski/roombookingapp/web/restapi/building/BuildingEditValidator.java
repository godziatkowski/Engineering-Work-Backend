package pl.godziatkowski.roombookingapp.web.restapi.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BuildingEditValidator
    extends AbstractValidator {

    private final IBuildingSnapshotFinder buildingSnapshotFinder;

    @Autowired
    public BuildingEditValidator(IBuildingSnapshotFinder buildingSnapshotFinder) {
        this.buildingSnapshotFinder = buildingSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BuildingEdit.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        BuildingEdit buildingEdit = (BuildingEdit) target;
        BuildingSnapshot buildingSnapshot = buildingSnapshotFinder.findOneById(buildingEdit.getId());
        if(buildingSnapshot == null){
            errors.rejectValue("id", "BuildingWithIdDoesNotExist");
        }
        if (otherBuildingWithGivenDataAlreadyExists(buildingSnapshot, buildingEdit)) {
            errors.rejectValue("name", "BuildingAlreadyExist");
        }

    }

    private boolean otherBuildingWithGivenDataAlreadyExists(BuildingSnapshot buildingSnapshot, BuildingEdit buildingEdit) {
        return buildingSnapshot != null && !buildingSnapshot.getId().equals(buildingEdit.getId());
    }

}
