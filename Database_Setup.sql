drop database if exists music_track;

create database music_track;

use music_track;

CREATE TABLE user(
    user_id INT Primary Key AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_login VARCHAR(255) NOT NULL,
    user_pass VARCHAR(255) NOT NULL,
    user_made DATETIME NOT NULL,
    user_type boolean NOT NULL
);

CREATE TABLE album(
    album_id INT Primary Key AUTO_INCREMENT NOT NULL,
    album_artist VARCHAR(255) NOT NULL,
    album_name VARCHAR(255) NOT NULL,
    album_songcount INT NOT NULL,
    album_ratingcount INT DEFAULT 0 , 
    album_genre VARCHAR(255),
    album_rating DOUBLE(3,2) DEFAULT 0 CHECK (album_rating BETWEEN 0 AND 5),
    album_release DATE NOT NULL
);

INSERT INTO album(album_id,album_name,album_artist,album_genre,album_songcount,album_release) VALUES
(NULL,'Metamodern Sounds in Country Music','Sturgill Simpson','Country',10,DATE '2014-05-13'), 
(NULL,'Psychopomp','Japanese Breakfast','Alternative',9,DATE '2016-04-01'),
(NULL, 'I Got A Name','Jim Croce','Folk Rock',11,DATE '1973-12-01'),
(NULL, 'But Seriously, Folks...','Joe Walsh','Rock',8,DATE '1978-05-16'),
(NULL,'Effloresce','Covet','Math Rock',6,DATE '2018-07-13'),
(NULL, 'GUTS(spilled','Olivia Rodgrigo','Pop',20,DATE '2023-09-08'),
(NULL,'MAYHEM','Lady Gaga','Pop', 17, DATE '2025-03-07'),
(NULL,'Mercurial World','Magdalena Bay','Pop',14,DATE '2021-10-08'),
(NULL,'In Through the Out Door','Led Zeppelin','Rock',7,DATE '1979-08-22'),
(NULL,'Bluey the Album(Music from the Original TV Series)', 'Bluey','Childrens Music',17,DATE '2021-01-22');



CREATE TABLE actvity(
    track_id INT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL, -- fk
    album_id INT NOT NULL, -- fk
    status INT NOT NULL,
    listened_count INT DEFAULT 0,
    listened_percent DOUBLE(5,2) CHECK (listened_percent BETWEEN 000.00 and 100.00),
    CONSTRAINT fk_album_id 
      FOREIGN KEY( album_id )
      REFERENCES album( album_id )
      ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_user_id
      FOREIGN KEY( user_id )
      REFERENCES user( user_id ) 
      ON DELETE CASCADE ON UPDATE CASCADE
);

