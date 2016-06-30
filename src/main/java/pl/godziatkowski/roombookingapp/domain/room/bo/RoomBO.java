package pl.godziatkowski.roombookingapp.domain.room.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import pl.godziatkowski.roombookingapp.domain.room.dto.RoomSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.event.KeeperAssignedEvent;
import pl.godziatkowski.roombookingapp.domain.room.event.KeeperClearedEvent;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyExistsException;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.BusinessObject;

@BusinessObject
public class RoomBO
    implements IRoomBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomBO.class);

    private final IRoomRepository roomRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RoomBO(IRoomRepository roomRepository, ApplicationEventPublisher eventPublisher) {
        this.roomRepository = roomRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public RoomSnapshot add(String name, RoomType roomType, Integer floor, Long seatsCount,
        Long computerStationsCount, Boolean projector, Boolean blackboard) {
        if (roomRepository.findOneByNameAndFloor(name, floor) != null) {
            throw new RoomAlreadyExistsException();
        }
        Room room = new Room(name, roomType, floor, seatsCount, computerStationsCount, projector, blackboard);
        room = roomRepository.saveAndFlush(room);
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
        roomRepository.saveAndFlush(room);
        LOGGER.info(
            "Edited room with id <{}>. Name: <{}>, Room type: <{}>, floor: <{}>, Count of seats: <{}>, Count of computer stations: <{}>, has Projector: <{}>, has Blackboard: <{}>.",
            id, name, roomType, floor, seatsCount, computerStationsCount, projector, blackboard);
    }

    @Override
    public void markAsUsable(Long id) {
        Room room = roomRepository.findOne(id);
        if (room != null) {
            room.markAsUsable();
            roomRepository.saveAndFlush(room);
            LOGGER.info("Room with id <{}> marked as usable", id);
        }
    }

    @Override
    public void markAsNotUsable(Long id) {
        Room room = roomRepository.findOne(id);
        if (room != null) {
            room.markAsNotUsable();
            roomRepository.saveAndFlush(room);
            LOGGER.info("Room with id <{}> marked as not usable", id);
        }
    }

    @Override
    public void assaignKeeper(Long id, long keeperId) {
        Room room = roomRepository.getOne(id);
        if (room != null) {
            room.assaignKeeper(keeperId);
            roomRepository.saveAndFlush(room);
            LOGGER.info("User with id <{}> has been assigned as a keeper of room <{}>", keeperId, id);
            KeeperAssignedEvent event = new KeeperAssignedEvent(this, keeperId, id);
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public void clearKeeper(Long id) {
        Room room = roomRepository.getOne(id);
        if (room != null) {
            room.clearKeeper();
            Long keeperId = room.toSnapshot().getKeeperId();
            roomRepository.saveAndFlush(room);
            LOGGER.info("Keeper role for room <{}> has been cleared", id);
            if (keeperId != null) {
                KeeperClearedEvent event = new KeeperClearedEvent(this, keeperId, id);
                eventPublisher.publishEvent(event);
            }
        }
    }

}
