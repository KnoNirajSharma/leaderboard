-- knoldus_leaderboard schema

-- !Ups

ALTER TABLE all_time_reputation DROP COLUMN IF EXISTS full_name CASCADE;
ALTER TABLE monthly_reputation DROP COLUMN IF EXISTS full_name CASCADE;
ALTER TABLE quarterly_reputation DROP COLUMN IF EXISTS full_name CASCADE;
