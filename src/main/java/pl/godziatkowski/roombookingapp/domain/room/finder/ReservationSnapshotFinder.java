package pl.godziatkowski.roombookingapp.domain.room.finder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.Finder;

@Finder
public class ReservationSnapshotFinder
    implements IReservationSnapshotFinder {

    private final IReservationRepository reservationRepository;
    private final IRoomRepository roomRepository;

    @Autowired
    public ReservationSnapshotFinder(IReservationRepository reservationRepository, IRoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<ReservationSnapshot> findAllActiveByRoomIdAndTimePeriod(Long roomId, LocalDateTime fromDate,
        LocalDateTime toDate) {
        Room room = roomRepository.findOne(roomId);

        List<Reservation> reservations = reservationRepository.findAllByRoomAndStartDateBetweenAndEndDateBetween(room,
            LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(fromDate),
            LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(toDate));
        return convert(reservations);
    }

    @Override
    public List<ReservationSnapshot> findAllByUserIdAndStartDateBetweenAndEndDateBetween(Long userId,
        LocalDateTime fromDate, LocalDateTime toDate) {
        List<Reservation> reservations = reservationRepository.findAllByUserIdAndIsCanceledFalseAndStartDateBetweenAndEndDateBetween(
            userId,
            LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(fromDate),
            LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(toDate));
        return convert(reservations);
    }

    @Override
    public ReservationSnapshot findOneById(Long id) {
        Reservation reservation = reservationRepository.findOne(id);

        return reservation != null ? reservation.toSnapshot() : null;
    }

    @Override
    public List<ReservationSnapshot> findAllByUserId(Long id) {
        List<Reservation> reservations = reservationRepository.findAllByUserIdAndIsCanceledFalse(id);
        return convert(reservations);
    }

    @Override
    public List<ReservationSnapshot> findAllByRoomIdAndActive(Long roomId) {
        List<Reservation> reservations = reservationRepository.findAllByRoomIdAndIsCanceledFalse(roomId);
        return convert(reservations);
    }

    @Override
    public List< ReservationSnapshot> findAllActiveByBuildingId(Long id) {
        List<Reservation> reservation = reservationRepository.findAllByIsCanceledFalseAndRoomInBuildingWith(id);
        return convert(reservation);
        
    }

    private List<ReservationSnapshot> convert(List<Reservation> reservation) {
        return reservation.stream().map(Reservation::toSnapshot).collect(Collectors.toList());
    }

}
