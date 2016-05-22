package pl.godziatkowski.roombookingapp.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.godziatkowski.roombookingapp.domain.room.entity.Room;

public interface IRoomRepository
    extends JpaRepository<Room, Long> {

    Room findOneByNameAndFloor(String name, Integer floor);

    Room findOneByName(String name);

    List<Room> findAllByIsUsableTrue();

    List<Room> findAllByFloor(Integer floor);
    
    @Query("SELECT r.floor FROM Room as r GROUP BY r.floor")
    List<Integer> findFloors();
}
