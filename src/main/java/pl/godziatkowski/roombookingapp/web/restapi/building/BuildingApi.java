package pl.godziatkowski.roombookingapp.web.restapi.building;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.godziatkowski.roombookingapp.domain.building.bo.IBuildingBO;
import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.domain.building.finder.IBuildingSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/building")
public class BuildingApi {

    private final IBuildingBO buildingBO;
    private final IBuildingSnapshotFinder buildingSnapshotFinder;
    private final Validator buildingNewValidator;
    private final Validator buildingEditValidator;

    @Autowired
    public BuildingApi(IBuildingBO buildingBO, IBuildingSnapshotFinder buildingSnapshotFinder,
        @Qualifier("buildingNewValidator") Validator buildingNewValidator,
        @Qualifier("buildingEditValidator") Validator buildingEditValidator) {
        this.buildingBO = buildingBO;
        this.buildingSnapshotFinder = buildingSnapshotFinder;
        this.buildingNewValidator = buildingNewValidator;
        this.buildingEditValidator = buildingEditValidator;
    }

    @InitBinder("buildingNew")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(buildingNewValidator);
    }

    @InitBinder("buildingEdit")
    protected void initEditBinder(WebDataBinder binder) {
        binder.setValidator(buildingEditValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<Building>> list() {
        List<BuildingSnapshot> buildingSnapshots = buildingSnapshotFinder.findAll();
        List<Building> buildings = buildingSnapshots.stream().map(Building::new).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(buildings);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usable")
    public HttpEntity<List<Building>> usableList() {
        List<BuildingSnapshot> buildingSnapshots = buildingSnapshotFinder.findAllUsable();
        List<Building> buildings = buildingSnapshots.stream().map(Building::new).collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(buildings);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public HttpEntity<Building> get(@PathVariable("id") Long id) {
        BuildingSnapshot buildingSnapshot = buildingSnapshotFinder.findOneById(id);

        return ResponseEntity
            .ok()
            .body(new Building(buildingSnapshot));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Building> register(@Valid @RequestBody BuildingNew buildingNew) {
        BuildingSnapshot buildingSnapshot = buildingBO.add(buildingNew.getName(), buildingNew.getAddress(),
            buildingNew.getCity());

        return ResponseEntity
            .ok()
            .body(new Building(buildingSnapshot));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Building> edit(@Valid @RequestBody BuildingEdit buildingEdit) {
        buildingBO.edit(buildingEdit.getId(), buildingEdit.getName(),
            buildingEdit.getAddress(), buildingEdit.getCity());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/markAsUsable", method = RequestMethod.PUT)
    public HttpEntity<Building> markAsUsable(@PathVariable("id") Long id) {
        buildingBO.markAsUsable(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/markAsNotUsable", method = RequestMethod.PUT)
    public HttpEntity<Building> markAsNotUsable(@PathVariable("id") Long id) {
        buildingBO.markAsNotUsable(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
