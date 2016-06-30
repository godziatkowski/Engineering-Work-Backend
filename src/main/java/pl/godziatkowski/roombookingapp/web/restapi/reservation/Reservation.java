package pl.godziatkowski.roombookingapp.web.restapi.reservation;

import java.time.LocalDateTime;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class Reservation {

    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Room room;
    private final Long userId;
    private final String userFirstName;
    private final String userLastName;
    private final ReservationStatus reservationStatus;
    private final Long acceptedBy;

    public Reservation(ReservationSnapshot reservationSnapshot, UserSnapshot userSnapshot) {
        this.id = reservationSnapshot.getId();
        this.createdAt = reservationSnapshot.getCreatedAt();
        this.startDate = reservationSnapshot.getStartDate();
        this.endDate = reservationSnapshot.getEndDate();
        this.room = new Room(reservationSnapshot.getRoomSnapshot());
        this.userId = reservationSnapshot.getUserId();
        this.userFirstName = userSnapshot.getFirstName();
        this.userLastName = userSnapshot.getLastName();
        this.reservationStatus = ReservationStatus.convertFromDomainValue(
            reservationSnapshot.getReservationStatus());
        this.acceptedBy = reservationSnapshot.getAcceptedBy();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Room getRoom() {
        return room;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public Long getAcceptedBy() {
        return acceptedBy;
    }

}
