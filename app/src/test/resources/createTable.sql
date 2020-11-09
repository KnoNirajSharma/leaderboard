CREATE TABLE IF NOT EXISTS knolder(
id INT AUTO_INCREMENT PRIMARY KEY,
full_name VARCHAR(100) NOT NULL,
wordpress_id VARCHAR(100) UNIQUE,
email_id VARCHAR(100) UNIQUE,
active_status BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS blog(
id INT PRIMARY KEY,
wordpress_id VARCHAR(100) NOT NULL,
published_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS techhub(
id VARCHAR(255) PRIMARY KEY,
email_id VARCHAR(100) NOT NULL,
uploaded_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS knolx(
id VARCHAR(255) PRIMARY KEY,
email_id VARCHAR(100) NOT NULL,
delivered_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS webinar(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
delivered_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS oscontribution(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
contributed_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS conference(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
delivered_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS book(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
published_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS researchpaper(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
published_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS all_time_reputation(
id INT AUTO_INCREMENT PRIMARY KEY,
knolder_id INT NOT NULL,
score INT NOT NULL,
rank INT NOT NULL
);

CREATE TABLE IF NOT EXISTS monthly_reputation(
id INT AUTO_INCREMENT PRIMARY KEY,
knolder_id INT NOT NULL,
score INT NOT NULL,
rank INT NOT NULL
);

CREATE TABLE IF NOT EXISTS quarterly_reputation(
id INT AUTO_INCREMENT PRIMARY KEY,
knolder_id INT NOT NULL,
streak VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS halloffame(
id INT AUTO_INCREMENT PRIMARY KEY,
knolder_id INT NOT NULL,
knolder_name VARCHAR(255) NOT NULL,
monthly_score INT NOT NULL,
all_time_score INT NOT NULL,
monthly_rank INT NOT NULL,
all_time_rank INT NOT NULL,
month VARCHAR(255),
year INT
);

CREATE TABLE IF NOT EXISTS monthlycontribution(
id INT AUTO_INCREMENT PRIMARY KEY,
knolder_id INT NOT NULL,
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

CREATE TABLE IF NOT EXISTS dynamicscoring(
id INT AUTO_INCREMENT PRIMARY KEY,
blog_score_multiplier INT NOT NULL,
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

CREATE TABLE IF NOT EXISTS Meetup(
id VARCHAR(255) PRIMARY KEY ,
email_id VARCHAR(100) NOT NULL,
delivered_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);
