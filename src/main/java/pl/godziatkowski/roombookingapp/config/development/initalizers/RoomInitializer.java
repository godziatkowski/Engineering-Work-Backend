package pl.godziatkowski.roombookingapp.config.development.initalizers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.sharedkernel.constant.Profiles;

@Component
@Profile(Profiles.DEVELOPMENT)
public class RoomInitializer
    implements IRoomInitlializer {

    private final IRoomBO rooomBO;

    @Autowired
    public RoomInitializer(IRoomBO rooomBO) {
        this.rooomBO = rooomBO;
    }

    @Override
    public List<Long> initializeRooms() {
        List<Long> roomIds = new ArrayList<>();

        roomIds.add(rooomBO.add("E1", RoomType.LECTURE_HALL, 0, 140L, 0L, true, true).getId());
        roomIds.add(rooomBO.add("E2", RoomType.LECTURE_HALL, 0, 40L, 0L, false, true).getId());
        roomIds.add(rooomBO.add("05", RoomType.LABORATORY, 0, 20L, 20L, false, false).getId());
        roomIds.add(rooomBO.add("314", RoomType.LABORATORY, 3, 14L, 14L, true, false).getId());

        return roomIds;
    }

}
