package pl.godziatkowski.roombookingapp.domain.building.bo;

import pl.godziatkowski.roombookingapp.domain.building.dto.BuildingSnapshot;

public interface IBuildingBO {

    BuildingSnapshot add(String name, String address, String city);

    void edit(Long id, String name, String address, String city);

    void markAsUsable(Long id);

    void markAsNotUsable(Long id);

}
