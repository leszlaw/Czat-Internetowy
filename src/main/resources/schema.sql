DROP TABLE IF EXISTS messages;
CREATE TABLE messages (id VARCHAR(255) NOT NULL auto_increment UNIQUE,
message VARCHAR(255),
receiver_id VARCHAR(255),
sender_id VARCHAR(255),
PRIMARY KEY (id),
CHECK (length(receiver_id)>2));