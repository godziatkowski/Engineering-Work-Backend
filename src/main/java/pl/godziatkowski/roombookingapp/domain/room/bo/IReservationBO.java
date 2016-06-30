package pl.godziatkowski.roombookingapp.domain.room.bo;

import java.time.LocalDateTime;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;

public interface IReservationBO {

    ReservationSnapshot reserveRoom(Long roomId, Long userId, LocalDateTime startDate, LocalDateTime endDate);

    void edit(Long id, Long roomId, LocalDateTime startDate, LocalDateTime endDate);

    void cancel(Long id);

    void accept(Long id, long acceptedBy);

    void reject(Long id, long rejectedBy);

}
