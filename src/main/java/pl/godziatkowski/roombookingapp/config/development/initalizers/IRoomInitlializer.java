package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.List;

public interface IRoomInitlializer {

    List<Long> initializeRooms(List<Long> buildingIds);

}
