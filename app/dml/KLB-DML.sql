CREATE DATABASE knoldus_leaderboard;
\c knoldus_leaderboard
CREATE TABLE knolder(id SERIAL PRIMARY KEY NOT NULL, full_name VARCHAR(100) NOT NULL, wordpress_id VARCHAR(100) UNIQUE);
CREATE TABLE blog(id INT PRIMARY KEY NOT NULL, wordpress_id VARCHAR(100) NOT NULL, published_on TIMESTAMP NOT NULL, title VARCHAR(255) NOT NULL);
CREATE TABLE all_time(id SERIAL PRIMARY KEY NOT NULL, knolder_id INT REFERENCES knolder(id) NOT NULL, overall_score INT NOT NULL, overall_rank INT NOT NULL);

