CREATE TABLE IF NOT EXISTS knolder(
id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
full_name VARCHAR(100) NOT NULL,
wordpress_id VARCHAR(100) UNIQUE,
email_id VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS blog(
id INT PRIMARY KEY NOT NULL,
wordpress_id VARCHAR(100) NOT NULL,
published_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS all_time(
id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
knolder_id INT NOT NULL,
number_of_blogs INT
);
