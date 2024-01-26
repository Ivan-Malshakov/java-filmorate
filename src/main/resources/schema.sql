DROP TABLE IF EXISTS users, films, mpa, genres, film_genres, friends, friends, likes;

CREATE TABLE IF NOT EXISTS users (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar NOT NULL,
  login varchar(255) NOT NULL,
  name varchar(255),
  birthday timestamp
);

CREATE TABLE IF NOT EXISTS films (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar(200) NOT NULL,
  release_date timestamp NOT NULL,
  rate int DEFAULT '0',
  duration int,
  mpa_id int
);

CREATE TABLE IF NOT EXISTS mpa (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255)
);

CREATE TABLE IF NOT EXISTS genres (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255)
);

CREATE TABLE IF NOT EXISTS film_genres (
  film_id int,
  genre_id int
);

CREATE TABLE IF NOT EXISTS friends (
  user_id int NOT NULL,
  friend_id int NOT NULL
);

CREATE TABLE IF NOT EXISTS likes (
  user_id int,
  film_id int
);

ALTER TABLE films ADD FOREIGN KEY (mpa_id) REFERENCES mpa (id);

ALTER TABLE film_genres ADD FOREIGN KEY (film_id) REFERENCES films (id);

ALTER TABLE film_genres ADD FOREIGN KEY (genre_id) REFERENCES genres (id);

ALTER TABLE friends ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE friends ADD FOREIGN KEY (friend_id) REFERENCES users (id);

ALTER TABLE likes ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE likes ADD FOREIGN KEY (film_id) REFERENCES films (id);