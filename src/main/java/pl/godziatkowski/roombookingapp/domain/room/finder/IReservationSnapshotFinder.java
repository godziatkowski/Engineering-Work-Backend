package pl.godziatkowski.roombookingapp.domain.room.finder;

import java.time.LocalDateTime;
import java.util.List;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;

public interface IReservationSnapshotFinder {
    
    List<ReservationSnapshot> findAllActiveByRoomIdAndTimePeriod(Long roomId, LocalDateTime startDate, LocalDateTime endDate);

    List<ReservationSnapshot> findAllByUserIdAndStartDateBetweenAndEndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    ReservationSnapshot findOneById(Long id);

    public List<ReservationSnapshot> findAllByUserId(Long id);

    public List<ReservationSnapshot> findAllByRoomIdAndActive(Long roomId);
    
    List<ReservationSnapshot> findAllPending();

    Integer getCountOfPendingReservations();

}
