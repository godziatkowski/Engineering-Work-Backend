ALTER TABLE reservation ADD COLUMN created_at DATETIME NOT NULL;
ALTER TABLE reservation ADD COLUMN reservation_status VARCHAR(30) NOT NULL;
ALTER TABLE reservation ADD COLUMN accepted_by BIGINT;
