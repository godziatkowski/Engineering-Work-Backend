package pl.godziatkowski.roombookingapp.domain.room.entity;

import java.time.LocalDateTime;

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
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.room.GivenReservationTest;
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.room.ThenReservationTest;
import pl.godziatkowski.roombookingapp.domain.room.entity.steps.room.WhenReservationTest;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ReservationTest
    extends ScenarioTest<GivenReservationTest, WhenReservationTest, ThenReservationTest> {

    private static final String CLAZZ = ReservationTest.class.getSimpleName();
    private static final Long LONG_VALUE = 1L;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime TWO_HOURS_FROM_NOW = LocalDateTime.now().plusHours(2L);

    @Autowired
    @ProvidedScenarioState
    private IRoomRepository roomRepository;

    @Autowired
    @ProvidedScenarioState
    private IReservationRepository reservationRepository;

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    public void should_edit_reservation() {
        given().a_room()
            .and().a_reservation(LONG_VALUE, NOW, TWO_HOURS_FROM_NOW)
            .and().reservation_data(LONG_VALUE, NOW.plusDays(1), TWO_HOURS_FROM_NOW.plusDays(1));
        when().edit_invoked();
        then().reservation_should_be_edited();
    }

    @Test
    public void should_cancel_reservation() {
        given().a_room()
            .and().a_reservation(LONG_VALUE, NOW, TWO_HOURS_FROM_NOW);
        when().cancel_reservation_is_invoked();
        then().reservation_should_be_canceled();
    }

    @Test
    public void should_return_snapshot() {
        given().a_room().and().a_reservation(LONG_VALUE, NOW, TWO_HOURS_FROM_NOW);
        when().to_snapshot_invoked();
        then().snapshot_should_be_returned();
    }

    @Test
    public void should_throw_exception_when_reservation_is_not_persisted() {
        given().a_room().and().a_not_persisted_reservation(LONG_VALUE, NOW, TWO_HOURS_FROM_NOW);
        when().to_snapshot_invoked();
        then().entityInStateNew_exception_should_be_thrown();
    }

}
