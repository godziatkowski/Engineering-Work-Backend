package pl.godziatkowski.roombookingapp.domain.building.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Building
    implements Serializable {

    private static final long serialVersionUID = 8219773317588672467L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 150)
    private String name;
    @NotNull
    @Size(min = 1, max = 150)
    private String address;
    @NotNull
    @Size(min = 1, max = 150)
    private String city;
    @NotNull
    private Boolean isUsable;

    protected Building() {
    }

    public Building(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.isUsable = true;
    }

    public void edit(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
    
    public void markAsUsable(){
        isUsable = true;
    }
    
    public void markAsNotUsable(){
        isUsable = false;
    }
    
    public BuildingSnapshot toSnapshot(){
        if(id == null){
            throw new EntityInStateNewException();
        }
        return new BuildingSnapshot(id, name, address, city, isUsable);
    }
}
