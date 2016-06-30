package pl.godziatkowski.roombookingapp.domain.room.event;

import org.springframework.context.ApplicationEvent;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;

public class ReservationRejectedEvent
    extends ApplicationEvent {

    private static final long serialVersionUID = -8857060452006461874L;
    private final ReservationSnapshot reservationSnapshot;

    public ReservationRejectedEvent(Object source, ReservationSnapshot reservationSnapshot) {
        super(source);
        this.reservationSnapshot = reservationSnapshot;
    }

    public ReservationSnapshot getReservationSnapshot() {
        return reservationSnapshot;
    }

}
