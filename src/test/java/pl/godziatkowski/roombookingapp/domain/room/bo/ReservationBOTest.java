package pl.godziatkowski.roombookingapp.domain.room.bo;

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
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation.GivenReservationBOTest;
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation.ThenReservationBOTest;
import pl.godziatkowski.roombookingapp.domain.room.bo.steps.reservation.WhenReservationBOTest;
import pl.godziatkowski.roombookingapp.domain.room.repository.IReservationRepository;
import pl.godziatkowski.roombookingapp.domain.room.repository.IRoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ReservationBOTest
    extends ScenarioTest<GivenReservationBOTest, WhenReservationBOTest, ThenReservationBOTest> {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime TWO_HOURS_FROM_NOW = NOW.plusHours(2L);
    private static final Long USER_ID = 5L;

    @Autowired
    @ProvidedScenarioState
    private IReservationBO reservationBO;
    @Autowired
    @ProvidedScenarioState
    private IRoomBO roomBO;
    @Autowired
    @ProvidedScenarioState
    private IReservationRepository reservationRepository;
    @Autowired
    @ProvidedScenarioState
    private IRoomRepository roomRepository;

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
    }
    
    //************* RESERVE

    @Test
    public void should_reserve_room_when_no_other_reservation_exists() {
        given().a_room()
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().reservation_should_be_created();
    }
    @Test
    public void should_reserve_room_when_other_reservation_ends_on_start_of_new_reservation() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.minusHours(1L), NOW)
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().reservation_should_be_created();
    }
    @Test
    public void should_reserve_room_when_other_reservation_starts_at_end_of_new_reservation() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, TWO_HOURS_FROM_NOW, TWO_HOURS_FROM_NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().reservation_should_be_created();
    }
    
    //******* Reserve exceptions

    @Test
    public void should_throw_RoomIsNotUsableException_when_trying_to_reserve_not_usable_room() {
        given().a_room().that_is_not_usable()
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomIsNotUsableException();
    }

    @Test
    public void should_throw_TimeOfReservationStartAfterEndTimeException_when_reservation_startDate_after_endDate() {
        given().a_room()
            .and().a_reservation_data(USER_ID, TWO_HOURS_FROM_NOW, NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_TimeOfReservationStartAfterEndTimeException();

    }

    @Test
    public void should_throw_RoomAlreadyReservedAtGivenDateAndTimeException_when_trying_to_reserve_already_reserved_room() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_and_ends_within_requested_period_of_time(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.plusHours(1L), TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW.plusHours(1L));
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_within_requested_time_and_ends_on_the_same_time(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.plusHours(1L), TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_at_the_same_time_and_end_within_requesetd_period_of_time(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_and_ends_at_the_same_time_as_requested_reservation(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_before_and_ends_within_time_of_requested_reservation(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.minusHours(1L), NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_ends_after_and_starts_within_time_of_requested_reservation(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.plusHours(1L), TWO_HOURS_FROM_NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_reservation_starts_before_and_ends_after_time_of_requested_reservation(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.minusHours(1L), TWO_HOURS_FROM_NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_one_reservation_ends_within_time_range_and_another_starts(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.minusHours(1L), NOW.plusHours(1L))
            .and().a_reservation_for_room(USER_ID, NOW.plusHours(1L), TWO_HOURS_FROM_NOW.plusHours(1L))
            .and().a_reservation_data(USER_ID, NOW, TWO_HOURS_FROM_NOW);
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    @Test
    public void already_reserved_room_previous_two_reservations_starts_and_ends_within_time_range(){
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, NOW.plusHours(1L))
            .and().a_reservation_for_room(USER_ID, NOW.plusHours(1L), TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW.minusHours(1L), TWO_HOURS_FROM_NOW.plusHours(1L));
        when().reserve_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }
    
    //************* EDIT
    
    @Test
    public void should_edit_reservation() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().another_room()
            .and().a_reservation_data(USER_ID, NOW.plusDays(1L), TWO_HOURS_FROM_NOW.plusDays(1L));
        when().edit_invoked();
        then().reservation_should_be_edited();
    }

    @Test
    public void should_throw_RoomIsNotUsableException_when_trying_to_edit_reservation_to_reserve_not_usable_room() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().another_room().that_is_not_usable()
            .and().a_reservation_data(USER_ID, NOW.plusDays(1L), TWO_HOURS_FROM_NOW.plusDays(1L));
        when().edit_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomIsNotUsableException();
    }

    @Test
    public void should_throw_TimeOfReservationStartAfterEndTimeException_when_trying_to_edit_reservation_with_startDate_after_endDate() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().another_room()
            .and().a_reservation_data(USER_ID, TWO_HOURS_FROM_NOW.plusDays(1L), NOW.plusDays(1L));
        when().edit_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_TimeOfReservationStartAfterEndTimeException();

    }

    @Test
    public void should_throw_RoomAlreadyReservedAtGivenDateAndTimeException_when_trying_to_edit_reservation_to_reserve_already_reserved_room() {
        given().a_room()
            .and().a_reservation_for_room(USER_ID, NOW.plusDays(1L), TWO_HOURS_FROM_NOW.plusDays(1L))
            .and().a_reservation_for_room(USER_ID, NOW, TWO_HOURS_FROM_NOW)
            .and().a_reservation_data(USER_ID, NOW.plusDays(1L), TWO_HOURS_FROM_NOW.plusDays(1L));
        when().edit_invoked();
        then().exception_should_be_thrown()
            .and().exception_is_of_type_RoomAlreadyReservedAtGivenDateAndTimeException();
    }

    @Test
    public void testEdit() {
    }

    @Test
    public void testCancel() {
    }

}
