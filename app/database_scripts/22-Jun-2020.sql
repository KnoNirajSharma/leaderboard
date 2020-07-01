-- knoldus_leaderboard schema

-- !Ups

CREATE TABLE knolx(id INT PRIMARY KEY NOT NULL, email_id VARCHAR(100) NOT NULL, delivered_on TIMESTAMP NOT NULL, title VARCHAR(255) NOT NULL);

-- !Downs

DROP TABLE knolx;
