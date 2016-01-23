CREATE TABLE room(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    room_type VARCHAR(30) NOT NULL,
    building_id BIGINT NOT NULL,
    floor INTEGER NOT NULL,
    seats_count BIGINT NOT NULL,
    computer_stations_count BIGINT NOT NULL,
    has_projector BOOLEAN,
    has_blackboard BOOLEAN, 
    is_usable BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

