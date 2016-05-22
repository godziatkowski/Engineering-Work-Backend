package pl.godziatkowski.roombookingapp.domain.room.exception;

public class RoomAlreadyExistsException
    extends RuntimeException {

    private static final long serialVersionUID = -6124896705746111358L;

    public RoomAlreadyExistsException() {
        super("Room with such name already exist on given floor");
    }
}
