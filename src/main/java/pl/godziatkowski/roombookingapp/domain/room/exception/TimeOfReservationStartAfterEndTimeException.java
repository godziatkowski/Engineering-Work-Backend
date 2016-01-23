package pl.godziatkowski.roombookingapp.domain.room.exception;

public class TimeOfReservationStartAfterEndTimeException
    extends RuntimeException {

    private static final long serialVersionUID = 6765743099072983209L;
    
    public TimeOfReservationStartAfterEndTimeException(){
        super("Time of reservation start cannot be after end time");
    }

}
