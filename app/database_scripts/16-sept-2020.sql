-- !Ups
create table halloffame(id SERIAL PRIMARY KEY, knolder_id INT NOT NULL, knolder_name VARCHAR(255) NOT NULL,
                         monthly_score INT NOT NULL, all_time_score INT NOT NULL, monthly_rank INT NOT NULL,
                         all_time_rank INT NOT NULL, month VARCHAR(255), year INT);
-- !Downs
drop table halloffame;
