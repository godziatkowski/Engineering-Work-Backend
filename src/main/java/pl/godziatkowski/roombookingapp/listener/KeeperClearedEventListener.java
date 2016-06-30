package pl.godziatkowski.roombookingapp.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import pl.godziatkowski.roombookingapp.domain.room.event.KeeperClearedEvent;
import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;

@Component
public class KeeperClearedEventListener
    implements ApplicationListener<KeeperClearedEvent> {

    private final IUserBO userBO;

    @Autowired
    public KeeperClearedEventListener(IUserBO userBO) {
        this.userBO = userBO;
    }

    @Override
    public void onApplicationEvent(KeeperClearedEvent event) {
        userBO.removeWatchedRoom(event.getUserId(), event.getRoomId());
    }

}
