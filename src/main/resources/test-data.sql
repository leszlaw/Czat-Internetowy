INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Alice','Eva');
INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Alice','Bob');
INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Bob','Alice');

INSERT INTO users (username,password,role) VALUES ('admin','$2y$10$repOODoMvaOaFWUVHJXFP.xivRUZUkKu5iMIiD755vOnafok0oUPm','admin');
INSERT INTO users (username,password,role) VALUES ('user','$2y$10$UIzmVccvEIJr5it1pxvGGeXvtLS952pco/1yl1LXhtyx91TYNrAE.','user');

INSERT INTO profiles (user_id,gender,description) VALUES ('1','FEMALE','I am admin');
INSERT INTO profiles (user_id,gender,description) VALUES ('2','MALE','I am user');
