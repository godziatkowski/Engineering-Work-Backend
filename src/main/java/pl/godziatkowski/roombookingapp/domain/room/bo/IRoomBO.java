package pl.godziatkowski.roombookingapp.domain.room.bo;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;

public interface IRoomBO {

    RoomSnapshot add(String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard);

    void edit(Long id, String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard);

    void markAsUsable(Long id);

    void markAsNotUsable(Long id);
    
    void assaignKeeper(Long id, long keeperId);
    void clearKeeper(Long id);

}
