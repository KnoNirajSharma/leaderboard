-- knoldus_leaderboard schema

-- !Ups

<<<<<<< HEAD
CREATE TABLE knolx(id VARCHAR(100) PRIMARY KEY NOT NULL, email_id VARCHAR(100) NOT NULL, delivered_on TIMESTAMP NOT NULL, title VARCHAR(255) NOT NULL);
=======
CREATE TABLE knolx(id INT PRIMARY KEY NOT NULL, email_id VARCHAR(100) NOT NULL, delivered_on TIMESTAMP NOT NULL, title VARCHAR(255) NOT NULL);
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b

-- !Downs

DROP TABLE knolx;
