package pl.godziatkowski.roombookingapp.domain.room.bo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomAlreadyReservedAtGivenDateAndTimeException;
import pl.godziatkowski.roombookingapp.domain.room.exception.RoomIsNotUsableException;
import pl.godziatkowski.roombookingapp.domain.room.exception.TimeOfReservationStartAfterEndTimeException;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.BusinessObject;

@BusinessObject
public class ReservationBO
    implements IReservationBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationBO.class);

    private final IReservationRepository reservationRepository;

    private final IRoomRepository roomRepository;

    @Autowired
    public ReservationBO(IReservationRepository reservationRepository, IRoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ReservationSnapshot reserveRoom(Long roomId, Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Room room = roomRepository.findOne(roomId);
        if (!room.toSnapshot().isUsable()) {
            throw new RoomIsNotUsableException();
        }
        if (endDate.isBefore(startDate)) {
            throw new TimeOfReservationStartAfterEndTimeException();
        }
        Timestamp fromDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(startDate);
        Timestamp toDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(endDate);
        if (!reservationRepository.findAllByRoomAndStartDateBetweenAndEndDateBetween(room, fromDate, toDate).isEmpty()) {
            throw new RoomAlreadyReservedAtGivenDateAndTimeException();
        }
        Reservation reservation = new Reservation(room, userId, startDate, endDate);
        reservation = reservationRepository.save(reservation);
        ReservationSnapshot reservationSnapshot = reservation.toSnapshot();

        LOGGER.info(
            "Created reservation for room with id <{}>. Room reserved by user with id <{}>. Reservation start at <{}> and ends at <{}>.",
            reservationSnapshot.getId(), roomId, userId, startDate, endDate);
        return reservationSnapshot;
    }

    @Override
    public void edit(Long id, Long roomId, LocalDateTime startDate, LocalDateTime endDate) {
        Room room = roomRepository.findOne(roomId);
        if (!room.toSnapshot().isUsable()) {
            throw new RoomIsNotUsableException();
        }
        if (endDate.isBefore(startDate)) {
            throw new TimeOfReservationStartAfterEndTimeException();
        }
        Timestamp fromDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(startDate);
        Timestamp toDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(endDate);
        List<Reservation> reservations
            = reservationRepository.findAllByRoomAndStartDateBetweenAndEndDateBetween(room, fromDate, toDate);
        Reservation reservation = null;
        if (!reservations.isEmpty()) {
            if (reservations.size() != 1 || !reservations.get(0).toSnapshot().getId().equals(id)) {
                throw new RoomAlreadyReservedAtGivenDateAndTimeException();
            }
            reservation = reservations.get(0);
        } else {
            reservation = reservationRepository.findOne(id);
        }
        reservation.edit(room, startDate, endDate);
        reservationRepository.save(reservation);
        LOGGER.info("Reservation with id <{}> edited. Reservation refers to room with id <{}>, from <{}> to <{}>",
            id, roomId, startDate, endDate);
    }

    @Override
    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findOne(id);
        if (reservation != null) {
            if (!reservation.toSnapshot().isCanceled()) {
                reservation.cancelReservation();
            }
            LOGGER.info("Reservation with id <{}> has been canceled");
        }
    }

}
