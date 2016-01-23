package pl.godziatkowski.roombookingapp.domain.room.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyExistsException;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.BusinessObject;

@BusinessObject
public class RoomBO
    implements IRoomBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomBO.class);

    private final IRoomRepository roomRepository;

    @Autowired
    public RoomBO(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomSnapshot add(String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard) {
        if (roomRepository.findOneByBuildingIdAndName(buildingId, name) != null) {
            throw new RoomAlreadyExistsException();
        }
        Room room = new Room(name, roomType, buildingId, floor, seatsCount, computerStationsCount, projector, blackboard);
        room = roomRepository.save(room);
        RoomSnapshot roomSnapshot = room.toSnapshot();
        LOGGER.info(
            "Created room with name <{}> and id <{}> in building with id <{}>. Room type is <{}> and it has <{}> seats and <{}> computer stations. Projector present: <{}>, Blackboard present: <{}>",
            roomSnapshot.getName(), roomSnapshot.getId(), roomSnapshot.getBuildingId(), roomSnapshot.getRoomType(),
            roomSnapshot.getSeatsCount(),
            roomSnapshot.getComputerStationsCount(), roomSnapshot.hasProjector(), roomSnapshot.hasBlackboard());
        return roomSnapshot;
    }

    @Override
    public void edit(Long id, String name, RoomType roomType, Long buildingId, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard) {
        Room room;
        if ((room = roomRepository.findOneByBuildingIdAndName(buildingId, name)) != null) {
            if (!room.toSnapshot().getId().equals(id)) {
                throw new RoomAlreadyExistsException();
            }
        } else {
            room = roomRepository.findOne(id);
        }
        room.edit(name, roomType, buildingId, floor, seatsCount, computerStationsCount, projector, blackboard);
        roomRepository.save(room);
        LOGGER.info(
            "Edited room with id <{}>. Name: <{}>, Room type: <{}>, Building id: <{}>, Count of seats: <{}>, Count of computer stations: <{}>, has Projector: <{}>, has Blackboard: <{}>.",
            id, name, roomType, buildingId, seatsCount, computerStationsCount, projector, blackboard);
    }

    @Override
    public void markAsUsable(Long id) {
        Room room = roomRepository.findOne(id);
        if (room != null) {
            room.markAsUsable();
            roomRepository.save(room);
            LOGGER.info("Room with id <{}> marked as usable", id);
        }
    }

    @Override
    public void markAsNotUsable(Long id) {
        Room room = roomRepository.findOne(id);
        if (room != null) {
            room.markAsNotUsable();
            roomRepository.save(room);
            LOGGER.info("Room with id <{}> marked as not usable", id);
        }
    }

    @Override
    public void markRoomsInBuildingAsNotUsable(Long buildingId) {
        List<Room> rooms = roomRepository.findAllByBuildingId(buildingId);
        rooms.forEach(room -> {
            room.markAsNotUsable();
        });
        roomRepository.save(rooms);
        LOGGER.info("<{}> rooms in building with id <{}> marked as not usable", rooms.size(), buildingId);
    }

}
