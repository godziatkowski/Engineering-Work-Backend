package pl.godziatkowski.roombookingapp.web.restapi.reservation;

import java.time.LocalDateTime;

public class ReservationNew {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long roomId;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
