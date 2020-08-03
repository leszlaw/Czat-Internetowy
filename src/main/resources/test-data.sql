INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Alice','Eva');
INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Alice','Bob');
INSERT INTO messages (message,receiver_id,sender_id) VALUES ('123','Bob','Alice');

INSERT INTO users (username,password,email,role) VALUES ('admin','$2y$10$repOODoMvaOaFWUVHJXFP.xivRUZUkKu5iMIiD755vOnafok0oUPm','admin@office.pl','admin');
INSERT INTO users (username,password,email,role) VALUES ('user','$2y$10$UIzmVccvEIJr5it1pxvGGeXvtLS952pco/1yl1LXhtyx91TYNrAE.','user@office.pl','user');
INSERT INTO users (username,password,email,role) VALUES ('alice','$2y$10$UIzmVccvEIJr5it1pxvGGeXvtLS952pco/1yl1LXhtyx91TYNrAE.','alice@office.pl','user');
INSERT INTO users (username,password,email,role) VALUES ('bob','$2y$10$UIzmVccvEIJr5it1pxvGGeXvtLS952pco/1yl1LXhtyx91TYNrAE.','Bob@office.pl','user');

INSERT INTO profiles (user_id,gender,description) VALUES ('1','FEMALE','I am admin');
INSERT INTO profiles (user_id,gender,description) VALUES ('2','MALE','I am user');

INSERT INTO contacts (owner_id,user_id) VALUES ('1','2');
INSERT INTO contacts (owner_id,user_id) VALUES ('2','1');
INSERT INTO contacts (owner_id,user_id) VALUES ('1','3');
INSERT INTO contacts (owner_id,user_id) VALUES ('1','4');
