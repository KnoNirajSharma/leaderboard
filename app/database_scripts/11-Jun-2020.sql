-- knoldus_leaderboard schema

-- !Ups

ALTER TABLE knolder ADD COLUMN active_status BOOLEAN;
UPDATE knolder SET active_status = true;
ALTER TABLE knolder ALTER COLUMN active_status SET NOT NULL;

-- !Downs

ALTER TABLE knolder DROP COLUMN IF EXISTS active_status CASCADE;
