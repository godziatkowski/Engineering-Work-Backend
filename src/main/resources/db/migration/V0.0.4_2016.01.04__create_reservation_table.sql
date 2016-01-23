CREATE TABLE reservation(
    id BIGINT NOT NULL AUTO_INCREMENT,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    is_canceled BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE reservation ADD CONSTRAINT FK_reservation FOREIGN KEY (room_id) REFERENCES room(id);


