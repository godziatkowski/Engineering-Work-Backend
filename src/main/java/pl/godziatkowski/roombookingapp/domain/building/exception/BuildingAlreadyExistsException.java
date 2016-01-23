package pl.godziatkowski.roombookingapp.domain.building.exception;

public class BuildingAlreadyExistsException
    extends RuntimeException {

    private static final long serialVersionUID = -2812732761479100205L;

    public BuildingAlreadyExistsException(){
        super("Building with such name already exist at given address in given city");
    }
}
