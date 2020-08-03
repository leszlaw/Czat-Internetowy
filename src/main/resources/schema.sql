DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;
CREATE TABLE messages (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
message VARCHAR(255),
receiver_id VARCHAR(255),
sender_id VARCHAR(255),
PRIMARY KEY (id));

CREATE TABLE users (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
username VARCHAR(255) UNIQUE,
password VARCHAR(255),
email VARCHAR(255),
role VARCHAR(255),
PRIMARY KEY (id));

CREATE TABLE profiles (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
user_id VARCHAR(255) UNIQUE,
gender VARCHAR(6),
description VARCHAR(255),
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users(id));