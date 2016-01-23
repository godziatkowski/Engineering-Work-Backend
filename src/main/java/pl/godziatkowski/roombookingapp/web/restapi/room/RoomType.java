package pl.godziatkowski.roombookingapp.web.restapi.room;

public enum RoomType {

    LECTURE_HALL,
    LABORATORY;

    public static pl.godziatkowski.roombookingapp.domain.room.entity.RoomType convertToDomainValue(RoomType restapiValue) {
        pl.godziatkowski.roombookingapp.domain.room.entity.RoomType roomType = null;

        switch (restapiValue) {
            case LABORATORY:
                roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.LABORATORY;
                break;
            case LECTURE_HALL:
                roomType = pl.godziatkowski.roombookingapp.domain.room.entity.RoomType.LECTURE_HALL;
                break;
            default:
                throw new AssertionError();
        }
        return roomType;
    }

}
