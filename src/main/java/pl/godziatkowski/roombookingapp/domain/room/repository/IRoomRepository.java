package pl.godziatkowski.roombookingapp.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.godziatkowski.roombookingapp.domain.room.entity.Room;

public interface IRoomRepository
    extends JpaRepository<Room, Long> {

    Room findOneByBuildingIdAndName(Long buildingId, String name);

    List<Room> findAllByName(String name);

    List<Room> findAllByIsUsableTrue();

    List<Room> findAllByBuildingId(Long buildingId);
}
