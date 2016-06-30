package pl.godziatkowski.roombookingapp.domain.room.dto;

import java.time.LocalDateTime;

import pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus;

public class ReservationSnapshot {

    private final Long id;
    private final LocalDateTime createdAt;
    private final RoomSnapshot roomSnapshot;
    private final Long userId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Boolean isCanceled;
    private final ReservationStatus reservationStatus;
    private final Long acceptedBy;

    public ReservationSnapshot(Long id, LocalDateTime createdAt, RoomSnapshot roomSnapshot, Long userId,
        LocalDateTime startDate,
        LocalDateTime endDate, Boolean isCanceled, ReservationStatus reservationStatus, Long acceptedBy) {
        this.id = id;
        this.roomSnapshot = roomSnapshot;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCanceled = isCanceled;
        this.reservationStatus = reservationStatus;
        this.acceptedBy = acceptedBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public Long getAcceptedBy() {
        return acceptedBy;
    }

}
