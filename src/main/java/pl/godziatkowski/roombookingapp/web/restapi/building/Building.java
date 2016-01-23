package pl.godziatkowski.roombookingapp.web.restapi.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;

@ApiModel
public class Building {

    private final Long id;    
    private final String name;
    private final String address;
    private final String city;
    private final Boolean isUsable;

    public Building(BuildingSnapshot buildingSnapshot) {
        this.id = buildingSnapshot.getId();
        this.name = buildingSnapshot.getName();
        this.address = buildingSnapshot.getAddress();
        this.city = buildingSnapshot.getCity();
        this.isUsable = buildingSnapshot.isUsable();
    }

    @ApiModelProperty("Building unique identifier")
    public Long getId() {
        return id;
    }

    @ApiModelProperty("Building name")
    public String getName() {
        return name;
    }

    @ApiModelProperty("Builing address")
    public String getAddress() {
        return address;
    }

    @ApiModelProperty("City in which building is located")
    public String getCity() {
        return city;
    }

    @ApiModelProperty("Is building usable")
    public Boolean isUsable() {
        return isUsable;
    }

}
