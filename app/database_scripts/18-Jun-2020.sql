-- knoldus_leaderboard schema

-- !Ups

ALTER TABLE all_time_reputation DROP COLUMN IF EXISTS full_name CASCADE;
ALTER TABLE monthly_reputation DROP COLUMN IF EXISTS full_name CASCADE;
ALTER TABLE quarterly_reputation DROP COLUMN IF EXISTS full_name CASCADE;

-- !Downs

ALTER TABLE all_time_reputation ADD COLUMN full_name VARCHAR(100);
UPDATE all_time_reputation AS all_time SET full_name = (SELECT full_name FROM knolder WHERE all_time.knolder_id = knolder.id);
ALTER TABLE all_time_reputation ALTER COLUMN full_name SET NOT NULL;

ALTER TABLE monthly_reputation ADD COLUMN full_name VARCHAR(100);
UPDATE monthly_reputation AS monthly SET full_name = (SELECT full_name FROM knolder WHERE monthly.knolder_id = knolder.id);
ALTER TABLE monthly_reputation ALTER COLUMN full_name SET NOT NULL;

ALTER TABLE quarterly_reputation ADD COLUMN full_name VARCHAR(100);
UPDATE quarterly_reputation AS quarterly SET full_name = (SELECT full_name FROM knolder WHERE quarterly.knolder_id = knolder.id);
ALTER TABLE quarterly_reputation ALTER COLUMN full_name SET NOT NULL;
