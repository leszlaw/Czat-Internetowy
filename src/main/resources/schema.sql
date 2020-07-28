DROP TABLE IF EXISTS messages;
CREATE TABLE messages (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
message VARCHAR(255),
receiver_id VARCHAR(255),
sender_id VARCHAR(255),
PRIMARY KEY (id));

DROP TABLE IF EXISTS users;
CREATE TABLE users (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
username VARCHAR(255) UNIQUE,
password VARCHAR(255),
role VARCHAR(255),
PRIMARY KEY (id));