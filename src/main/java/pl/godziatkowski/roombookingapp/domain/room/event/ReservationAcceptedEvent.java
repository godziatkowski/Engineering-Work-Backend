package pl.godziatkowski.roombookingapp.domain.room.event;

import org.springframework.context.ApplicationEvent;

import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;

public class ReservationAcceptedEvent
    extends ApplicationEvent {

    private static final long serialVersionUID = -952957180320649368L;
    private final ReservationSnapshot reservationSNapshot;

    public ReservationAcceptedEvent(Object source, ReservationSnapshot reservationSnapshot) {
        super(source);
        this.reservationSNapshot = reservationSnapshot;
    }

    public ReservationSnapshot getReservationSnapshot() {
        return reservationSNapshot;
    }

}
