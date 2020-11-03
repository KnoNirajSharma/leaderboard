-- !Ups
ALTER TABLE referencepaper
RENAME TO researchpaper;
-- !Downs
ALTER TABLE researchpaper
RENAME TO referencepaper;
