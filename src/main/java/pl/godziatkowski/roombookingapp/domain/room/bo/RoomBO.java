package pl.godziatkowski.roombookingapp.domain.room.bo;


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
    public RoomSnapshot add(String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard) {
        if (roomRepository.findOneByNameAndFloor(name, floor) != null) {
            throw new RoomAlreadyExistsException();
        }
        Room room = new Room(name, roomType, floor, seatsCount, computerStationsCount, projector, blackboard);
        room = roomRepository.save(room);
        RoomSnapshot roomSnapshot = room.toSnapshot();
        LOGGER.info(
            "Created room with name <{}> and id <{}> at <{}> floor. Room type is <{}> and it has <{}> seats and <{}> computer stations. Projector present: <{}>, Blackboard present: <{}>",
            roomSnapshot.getName(), roomSnapshot.getId(), 
            roomSnapshot.getFloor(),
            roomSnapshot.getRoomType(),
            roomSnapshot.getSeatsCount(),
            roomSnapshot.getComputerStationsCount(), roomSnapshot.hasProjector(), roomSnapshot.hasBlackboard());
        return roomSnapshot;
    }

    @Override
    public void edit(Long id, String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard) {
        Room room;
        if ((room = roomRepository.findOneByNameAndFloor(name, floor)) != null) {
            if (!room.toSnapshot().getId().equals(id)) {
                throw new RoomAlreadyExistsException();
            }
        } else {
            room = roomRepository.findOne(id);
        }
        room.edit(name, roomType, floor, seatsCount, computerStationsCount, projector, blackboard);
        roomRepository.save(room);
        LOGGER.info(
            "Edited room with id <{}>. Name: <{}>, Room type: <{}>, floor: <{}>, Count of seats: <{}>, Count of computer stations: <{}>, has Projector: <{}>, has Blackboard: <{}>.",
            id, name, roomType, floor, seatsCount, computerStationsCount, projector, blackboard);
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

}
