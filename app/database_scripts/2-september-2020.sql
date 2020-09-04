-- !Ups
Alter table conference alter column id Type varchar(255);
Alter table oscontribution alter column id Type varchar(255);
-- !Downs
Alter table conference alter column id Type varchar(100);
Alter table oscontribution alter column id Type varchar(100);
