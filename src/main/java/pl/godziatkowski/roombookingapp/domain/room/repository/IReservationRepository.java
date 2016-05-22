package pl.godziatkowski.roombookingapp.domain.room.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.godziatkowski.roombookingapp.domain.room.entity.Reservation;
import pl.godziatkowski.roombookingapp.domain.room.entity.Room;

public interface IReservationRepository
    extends JpaRepository<Reservation, Long> {

    @Query(
        "SELECT r FROM Reservation r INNER JOIN r.room rm WHERE rm = :room AND r.isCanceled IS FALSE AND ((r.startDate BETWEEN :fromDate AND :toDate OR r.endDate BETWEEN :fromDate AND :toDate) OR (:fromDate BETWEEN r.startDate AND r.endDate OR :toDate BETWEEN r.startDate AND r.endDate)) AND r.startDate != :toDate AND r.endDate != :fromDate")
    List<Reservation> findAllByRoomAndStartDateBetweenAndEndDateBetween(@Param("room") Room room,
        @Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);

    @Query("SELECT r FROM Reservation r INNER JOIN r.room rm WHERE rm = :room AND r.isCanceled IS FALSE AND r.startDate > :fromDate AND r.endDate < :toDate")
    List<Reservation> findAllByRoomAndIsCanceledFalseAndStartDateBetweenAndEndDateBetween(@Param("room") Room room, @Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);

    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId AND r.isCanceled IS FALSE AND r.startDate > :fromDate AND r.endDate < :toDate")
    List<Reservation> findAllByUserIdAndIsCanceledFalseAndStartDateBetweenAndEndDateBetween(@Param("userId") Long userId, @Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);

    public List<Reservation> findAllByUserIdAndIsCanceledFalse(Long id);

    public List<Reservation> findAllByRoomIdAndIsCanceledFalse(Long roomId);
    @Query("SELECT r FROM Reservation r INNER JOIN r.room rm WHERE r.isCanceled IS FALSE AND rm.floor = :floor")
    public List<Reservation> findAllByIsCanceledFalseAndRoomOnFloor(@Param("floor") Integer floor);

}
