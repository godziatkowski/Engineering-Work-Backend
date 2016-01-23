package pl.godziatkowski.roombookingapp.domain.room.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import pl.godziatkowski.roombookingapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.godziatkowski.roombookingapp.domain.room.dto.ReservationSnapshot;
import pl.godziatkowski.roombookingapp.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Reservation
    implements Serializable {

    private static final long serialVersionUID = 308004708963800220L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;

    @NotNull
    private Long userId;

    @NotNull
    private Timestamp startDate;

    @NotNull
    private Timestamp endDate;
    
    @NotNull
    private Boolean isCanceled;

    protected Reservation() {
    }

    public Reservation(Room room, Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        this.room = room;
        this.userId = userId;
        this.startDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(startDate);
        this.endDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(endDate);
        isCanceled = false;
    }

    public void edit(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        this.room = room;
        this.startDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(startDate);
        this.endDate = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(endDate);
    }
    
    public void cancelReservation(){
        this.isCanceled = true;
    }

    public ReservationSnapshot toSnapshot() {
        if (id == null) {
            throw new EntityInStateNewException();
        }
        LocalDateTime startDate = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.startDate);
        LocalDateTime endDate = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.endDate);

        return new ReservationSnapshot(id, room.toSnapshot(), userId, startDate, endDate, isCanceled);
    }

}
