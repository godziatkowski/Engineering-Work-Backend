package pl.godziatkowski.roombookingapp.domain.room.bo;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;

public interface IRoomBO {

    RoomSnapshot add(String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard);

    void edit(Long id, String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard);

    void markAsUsable(Long id);

    void markAsNotUsable(Long id);

    void markRoomsInBuildingAsNotUsable(Long buildingId);

}
