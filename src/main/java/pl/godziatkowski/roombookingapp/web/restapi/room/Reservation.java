package pl.godziatkowski.roombookingapp.web.restapi.room;

import java.time.LocalDateTime;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public class Reservation {

    private final Long id;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long roomId;
    private final String roomName;
    private final RoomType roomType;
    private final Long userId;
    private final String userFirstName;
    private final String userLastName;

    public Reservation(ReservationSnapshot reservationSnapshot, UserSnapshot userSnapshot) {
        this.id = reservationSnapshot.getId();
        this.startDate = reservationSnapshot.getStartDate();
        this.endDate = reservationSnapshot.getEndDate();
        this.roomId = reservationSnapshot.getRoomSnapshot().getId();
        this.roomName = reservationSnapshot.getRoomSnapshot().getName();
        this.roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.convertToRestapiValue(
            reservationSnapshot.getRoomSnapshot().getRoomType());
        this.userId = reservationSnapshot.getUserId();
        this.userFirstName = userSnapshot.getFirstName();
        this.userLastName = userSnapshot.getLastName();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public RoomType getRoomType() {
        return roomType;
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

}
