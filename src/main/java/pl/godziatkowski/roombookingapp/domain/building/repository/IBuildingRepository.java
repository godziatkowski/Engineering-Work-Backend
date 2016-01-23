package pl.godziatkowski.roombookingapp.domain.building.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.godziatkowski.roombookingapp.domain.building.entity.Building;

public interface IBuildingRepository extends JpaRepository<Building, Long>{

    Building findOneByNameAndCityAndAddress( String name, String city, String address);
    
    List<Building> findAllByIsUsableTrue();
}
