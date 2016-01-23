package pl.godziatkowski.roombookingapp.web.restapi.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.RestValidator;
import pl.godziatkowski.roombookingapp.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BuildingNewValidator
    extends AbstractValidator {

    private final IBuildingSnapshotFinder buildingSnapshotFinder;

    @Autowired
    public BuildingNewValidator(IBuildingSnapshotFinder buildingSnapshotFinder) {
        this.buildingSnapshotFinder = buildingSnapshotFinder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BuildingNew.class.isAssignableFrom(clazz);
    }

    @Override
    public void customValidation(Object target, Errors errors) {
        BuildingNew buildingNew = (BuildingNew) target;
        if (otherBuildingWithGivenDataAlreadyExists(buildingNew)) {
            errors.rejectValue("name", "BuildingAlreadyExist");
        }

    }

    private boolean otherBuildingWithGivenDataAlreadyExists(BuildingNew buildingNew) {
        return buildingSnapshotFinder.findOneByNameAndAddressAndCity(buildingNew.getName(),
            buildingNew.getAddress(), buildingNew.getCity()) != null;
    }

}
