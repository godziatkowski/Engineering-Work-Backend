package pl.godziatkowski.roombookingapp.domain.room.entity;

public enum RoomType {

    LECTURE_HALL,
    LABORATORY;

    public static pl.godziatkowski.roombookingapp.web.restapi.room.RoomType convertToRestapiValue(RoomType domainValue) {
        pl.godziatkowski.roombookingapp.web.restapi.room.RoomType roomType = null;

        switch (domainValue) {
            case LABORATORY:
                roomType = pl.godziatkowski.roombookingapp.web.restapi.room.RoomType.LABORATORY;
                break;
            case LECTURE_HALL:
                roomType = pl.godziatkowski.roombookingapp.web.restapi.room.RoomType.LECTURE_HALL;
                break;
            default:
                throw new AssertionError();
        }
        return roomType;
    }

}
