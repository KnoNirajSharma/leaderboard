-- !Ups

ALTER TABLE dynamicscoring ADD meetup_score_multiplier INT NOT NULL DEFAULT 1;
ALTER TABLE monthlycontribution ADD meetup_score INT NOT NULL DEFAULT 0;

-- !Downs

ALTER TABLE dynamicscoring DROP COLUMN IF EXISTS meetup_score_multiplier CASCADE;
ALTER TABLE monthlycontribution DROP COLUMN IF EXISTS meetup_score CASCADE;
