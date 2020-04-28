CREATE DATABASE knoldus_leaderboard;
\c knoldus_leaderboard
CREATE TABLE knolder(id SERIAL PRIMARY KEY NOT NULL, full_name VARCHAR(100) NOT NULL, wordpress_id VARCHAR(100) UNIQUE, email_id VARCHAR(100) UNIQUE);
CREATE TABLE blog(id INT PRIMARY KEY NOT NULL, wordpress_id VARCHAR(100) NOT NULL, published_on TIMESTAMP NOT NULL, title VARCHAR(255) NOT NULL);
CREATE TABLE all_time(id SERIAL PRIMARY KEY NOT NULL, knolder_id INT REFERENCES knolder(id) NOT NULL, number_of_blogs INT);
insert into knolder(full_name,wordpress_id)values('Niharika Datta','niharika76');
insert into knolder(full_name,wordpress_id)values('Ruchika Dubey','ruchika0524');
insert into knolder(full_name,wordpress_id)values('Himanshu Gupta','himanshuknolder');
insert into knolder(full_name,wordpress_id)values('Vikas Hazrati','knoldus');
insert into knolder(full_name,wordpress_id)values('Deepak Mehra','deepak028');
insert into knolder(full_name,wordpress_id)values('Girish Chandra Bharti','girishbharti');

