package pl.godziatkowski.roombookingapp.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.room.event.KeeperAssignedEvent;
import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;

@Component
public class KeeperAssignedEventListener
    implements ApplicationListener<KeeperAssignedEvent> {

    private final IUserBO userBO;

    @Autowired
    public KeeperAssignedEventListener(IUserBO userBO) {
        this.userBO = userBO;
    }

    @Override
    public void onApplicationEvent(KeeperAssignedEvent event) {
        userBO.addWatchedRoom(event.getUserId(), event.getRoomId());
    }

}
