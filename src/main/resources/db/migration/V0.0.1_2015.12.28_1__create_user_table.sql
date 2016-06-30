CREATE TABLE user (
	id BIGINT (20) NOT NULL AUTO_INCREMENT,
	login VARCHAR(255) NOT NULL,
	password VARCHAR(60) DEFAULT NULL,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY login (login),
	UNIQUE KEY idx_user_login(login)
	);

CREATE TABLE user_user_roles (
    user_id BIGINT NOT NULL,
    user_roles VARCHAR(25) NOT NULL
    );

CREATE TABLE user_watched_rooms (
	user_id BIGINT NOT NULL,
	watched_rooms BIGINT NOT NULL
	);