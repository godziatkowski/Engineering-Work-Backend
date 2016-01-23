package pl.godziatkowski.roombookingapp.domain.room.exception;

public class RoomAlreadyReservedAtGivenDateAndTimeException
    extends RuntimeException {

    private static final long serialVersionUID = -2325689898035680257L;

    public RoomAlreadyReservedAtGivenDateAndTimeException() {
        super("Given room is already reserved at given time and date");
    }

    
}
