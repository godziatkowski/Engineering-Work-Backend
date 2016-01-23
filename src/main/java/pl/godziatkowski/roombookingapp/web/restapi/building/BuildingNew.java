package pl.godziatkowski.roombookingapp.web.restapi.building;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class BuildingNew {

    @NotNull
    @Size(min = 3, max = 150)
    private String name;
    @NotNull
    @Size(min = 3, max = 150)
    private String address;
    @NotNull
    @Size(min = 3, max = 150)
    private String city;

    @ApiModelProperty("Building name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty("Builing address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ApiModelProperty("City in which building is located")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
