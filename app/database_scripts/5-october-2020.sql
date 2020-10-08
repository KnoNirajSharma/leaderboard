-- !Ups
CREATE TABLE IF NOT EXISTS dynamicscoring(id Serial PRIMARY KEY,blog_score_multiplier INT NOT NULL,
knolx_score_multiplier INT NOT NULL,
webinar_score_multiplier INT NOT NULL,
oscontribution_score_multiplier INT NOT NULL,
techhub_score_multiplier INT NOT NULL,
conference_score_multiplier INT NOT NULL,
book_score_multiplier INT NOT NULL,
researchpaper_score_multiplier INT NOT NULL,
month VARCHAR(255),
year INT NOT NULL
);

CREATE TABLE IF NOT EXISTS monthlycontribution(id serial PRIMARY KEY,knolder_id INT NOT NULL,
blog_score INT NOT NULL,
knolx_score INT NOT NULL,
webinar_score INT NOT NULL,
techhub_score INT NOT NULL,
oscontribution_score INT NOT NULL,
conference_score INT NOT NULL,
book_score INT NOT NULL,
researchpaper_score INT NOT NULL,
month VARCHAR(255),
year INT NOT NULL
);
-- !Downs
DROP TABLE dynamicscoring IF EXISTS;
DROP TABLE monthlycontribution IF EXISTS;
