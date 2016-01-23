package pl.godziatkowski.roombookingapp.domain.room.dto;

import java.time.LocalDateTime;

public class ReservationSnapshot {

    private final Long id;
    private final RoomSnapshot roomSnapshot;
    private final Long userId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Boolean isCanceled;

    public ReservationSnapshot(Long id, RoomSnapshot roomSnapshot, Long userId, LocalDateTime startDate,
        LocalDateTime endDate, Boolean isCanceled) {
        this.id = id;
        this.roomSnapshot = roomSnapshot;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCanceled = isCanceled;

    }

    public Long getId() {
        return id;
    }

    public RoomSnapshot getRoomSnapshot() {
        return roomSnapshot;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Boolean isCanceled() {
        return isCanceled;
    }

}
