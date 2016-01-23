package pl.godziatkowski.roombookingapp.domain.room.exception;

public class RoomIsNotUsableException
    extends RuntimeException {

    private static final long serialVersionUID = -6124896705746111358L;

    public RoomIsNotUsableException() {
        super("Cannot reserve not usable room");
    }
}
