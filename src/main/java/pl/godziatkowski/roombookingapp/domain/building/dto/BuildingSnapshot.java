package pl.godziatkowski.roombookingapp.domain.building.dto;

public class BuildingSnapshot {

    private final Long id;
    private final String name;
    private final String address;
    private final String city;
    private final Boolean isUsable;

    public BuildingSnapshot(Long id, String name, String address, String city, Boolean isUsable) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.isUsable = isUsable;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
    
    public Boolean isUsable(){
        return isUsable;
    }

}
