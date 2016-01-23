package pl.godziatkowski.roombookingapp.domain.room.bo;

import pl.godziatkowski.roombookingapp.domain.room.bo.IRoomBO;

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
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.room.GivenRoomBOTest;
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.room.ThenRoomBOTest;
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.room.WhenRoomBOTest;
import pl.godziatkowski.roombookingapp.domain.room.entity.RoomType;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RoomBOTest
    extends ScenarioTest<GivenRoomBOTest, WhenRoomBOTest, ThenRoomBOTest> {

    private static final String CLAZZ = RoomBOTest.class.getSimpleName();
    private static final long LONG_VALUE = 1L;
    private static final int INTEGER_VALUE = 1;

    @Autowired
    @ProvidedScenarioState
    private IRoomRepository roomRepository;
    @Autowired
    @ProvidedScenarioState
    private IRoomBO roomBO;

    @After
    public void tearDown() {
        roomRepository.deleteAll();
    }

    @Test
    public void should_add_new_room() {
        given().roomData(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
            LONG_VALUE, LONG_VALUE, true, true);
        when().add_invoked_with_given_room_data();
        then().room_should_be_added();
    }

    @Test
    public void should_throw_RoomAlreadyExistException_when_add_new_room_with_data_of_existing_room() {
        given().an_existing_room(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
            LONG_VALUE, LONG_VALUE, true, true)
            .and().roomData(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
                LONG_VALUE, LONG_VALUE, true, true);
        when().add_invoked_with_given_room_data();
        then().exception_should_be_thrown();
    }

    @Test
    public void should_edit_room() {
        given().an_existing_room(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
            LONG_VALUE, LONG_VALUE, true, true)
            .and().roomData(CLAZZ + 1, RoomType.LABORATORY, LONG_VALUE + 1, INTEGER_VALUE + 1,
                LONG_VALUE + 1, LONG_VALUE + 1, false, false);
        when().edit_invoked();
        then().room_should_be_edited();
    }

    @Test
    public void should_throw_RoomAlreadyExistException_when_edit_room_with_data_of_other_existing_room() {
        given().an_existing_room(CLAZZ + 1, RoomType.LABORATORY, LONG_VALUE + 1, INTEGER_VALUE + 1,
                LONG_VALUE + 1, LONG_VALUE + 1, false, false)
            .and().an_existing_room(CLAZZ, RoomType.LECTURE_HALL, LONG_VALUE, INTEGER_VALUE,
            LONG_VALUE, LONG_VALUE, true, true)
            .and().roomData(CLAZZ + 1, RoomType.LABORATORY, LONG_VALUE + 1, INTEGER_VALUE + 1,
                LONG_VALUE + 1, LONG_VALUE + 1, false, false);
        when().edit_invoked();
        then().exception_should_be_thrown();
    }

    @Test
    public void testMarkAsUsable() {
        given().an_existing_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true).and().room_is_not_usable();
        when().mark_as_usable_invoked();
        then().room_should_be_marked_as_usable();
    }

    @Test
    public void testMarkAsNotUsable() {
        given().an_existing_room(CLAZZ, RoomType.LABORATORY, LONG_VALUE, INTEGER_VALUE, LONG_VALUE, LONG_VALUE, true,
            true);
        when().mark_as_not_usable_invoked();
        then().room_should_be_marked_as_not_usable();
    }

}
