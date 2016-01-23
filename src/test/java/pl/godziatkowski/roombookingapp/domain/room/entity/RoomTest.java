package pl.godziatkowski.roombookingapp.domain.room.entity;

import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.godziatkowski.roombookingapp.Application;
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation.GivenRoomTest;
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation.ThenRoomTest;
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.reservation.WhenRoomTest;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RoomTest
    extends ScenarioTest<GivenRoomTest, WhenRoomTest, ThenRoomTest> {

    private static final String CLAZZ = RoomTest.class.getSimpleName();
    private static final long LONG_VALUE = 1L;
    private static final int INTEGER_VALUE = 1;
    @Autowired
    @ProvidedScenarioState
    private IRoomRepository roomRepository;

    @After
    public void tearDown() {
        roomRepository.deleteAll();
    }

    @Test
    public void should_edit_room() {
        given()
            .an_existing_room(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
                LONG_VALUE, LONG_VALUE, true, true)
            .and()
            .room_data(CLAZZ, RoomType.LABORATORY, LONG_VALUE + 1, INTEGER_VALUE + 1,
                LONG_VALUE + 1, LONG_VALUE + 1, false, false);

        when().edit_invoked();
        then().room_should_be_edited();
    }

    @Test
    public void should_mark_room_as_usable() {
        given().an_existing_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true).and().room_is_marked_as_not_usable();
        when().mark_as_usable_invoked();
        then().room_should_be_marked_as_usable();
    }

    @Test
    public void should_mark_room_as_not_usable() {
        given().an_existing_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true);
        when().mark_as_not_usable_invoked();
        then().room_should_be_marked_as_not_usable();
    }

    @Test
    public void should_return_snapshot_of_room() {
        given().an_existing_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true);
        when().to_snapshot_invoked();
        then().snapshot_should_be_returned();
    }
    @Test
    public void should_throw_entity_in_state_new_exception_when_to_snapshot_invoked_on_not_persisted_room() {
        given().not_persisted_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true);
        when().to_snapshot_invoked();
        then().exception_should_be_thrown();
    }

}
