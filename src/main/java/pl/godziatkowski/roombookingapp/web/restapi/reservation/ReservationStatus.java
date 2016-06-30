package pl.godziatkowski.roombookingapp.web.restapi.reservation;

public enum ReservationStatus {

    PENDING,
    ACCEPTED;

    public static pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus convertToDomainValue(
        ReservationStatus restapiValue) {
        pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus reservationStatus = null;

        switch (restapiValue) {
            case PENDING:
                reservationStatus = pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus.PENDING;
                break;
            case ACCEPTED:
                reservationStatus = pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus.ACCEPTED;
                break;
            default:
                throw new AssertionError();
        }
        return reservationStatus;
    }

    public static ReservationStatus convertFromDomainValue(
        pl.godziatkowski.roombookingapp.domain.room.entity.ReservationStatus domainValue) {
        ReservationStatus reservationStatus = null;

        switch (domainValue) {
            case PENDING:
                reservationStatus = PENDING;
                break;
            case ACCEPTED:
                reservationStatus = ACCEPTED;
                break;
            default:
                throw new AssertionError();
        }
        return reservationStatus;
    }
}
