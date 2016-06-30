package pl.godziatkowski.roombookingapp.domain.room.event;

import org.springframework.context.ApplicationEvent;

public class KeeperClearedEvent
    extends ApplicationEvent {

    private static final long serialVersionUID = 1551790418644853480L;
    private final long userId;
    private final long roomId;

    public KeeperClearedEvent(Object source, long userId, long roomId) {
        super(source);
        this.userId = userId;
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

}
