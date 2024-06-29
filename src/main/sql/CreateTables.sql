CREATE TABLE movies (
    id 		INT AUTO_INCREMENT PRIMARY KEY,
    titulo 	VARCHAR(255),
    diretor VARCHAR(255),
    genero 	VARCHAR(100)
);

CREATE TABLE users (
	id 			INT AUTO_INCREMENT PRIMARY KEY,
	userName 	VARCHAR(255),
	email 		VARCHAR(255),
	password	VARCHAR(255)
);

CREATE TABLE ratings (
	movieId 	INT,
	userId		INT,
	rating		TINYINT,
	FOREIGN KEY (movieId) REFERENCES movies(id),
	FOREIGN KEY (userId) REFERENCES users(id)
);

SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS rating FROM movies AS m
INNER JOIN ratings AS r ON r.movieId = m.id
GROUP BY m.id, m.titulo, m.diretor, m.genero;

 SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m  INNER JOIN ratings AS r ON r.movieId = m.id WHERE m.titulo LIKE '%Lord%' GROUP BY m.id, m.titulo, m.diretor, m.genero  ORDER BY ratingCount DESC

SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m  INNER JOIN ratings AS r ON r.movieId = m.id  GROUP BY m.id, m.titulo, m.diretor, m.genero ORDER BY ratingAverage LIMIT 10

-- Inserção de registros na tabela de filmes
INSERT INTO movies (titulo, diretor, genero) VALUES
('The Shawshank Redemption', 'Frank Darabont', 'Drama'),
('The Godfather', 'Francis Ford Coppola', 'Crime, Drama'),
('The Dark Knight', 'Christopher Nolan', 'Action, Crime, Drama'),
('Pulp Fiction', 'Quentin Tarantino', 'Crime, Drama'),
('The Lord of the Rings: The Return of the King', 'Peter Jackson', 'Adventure, Drama, Fantasy'),
('Forrest Gump', 'Robert Zemeckis', 'Drama, Romance'),
('Inception', 'Christopher Nolan', 'Action, Adventure, Sci-Fi'),
('Fight Club', 'David Fincher', 'Drama'),
('The Matrix', 'Lana Wachowski, Lilly Wachowski', 'Action, Sci-Fi'),
('Goodfellas', 'Martin Scorsese', 'Biography, Crime, Drama');

-- Inserção de registros na tabela users
INSERT INTO users (userName, email, password) VALUES
('john_doe', 'john.doe@example.com', 'password123'),
('jane_smith', 'jane.smith@example.com', 'securepass456'),
('alice_jones', 'alice.jones@example.com', 'mypassword789'),
('bob_brown', 'bob.brown@example.com', 'password456'),
('charlie_davis', 'charlie.davis@example.com', 'password789');

-- Inserção de registros na tabela ratings
INSERT INTO ratings (movieId, userId, rating) VALUES
(1, 1, 9),
(2, 1, 10),
(3, 1, 8),
(4, 2, 7),
(5, 2, 9),
(6, 2, 8),
(7, 3, 10),
(8, 3, 9),
(9, 3, 7),
(10, 4, 6),
(1, 4, 9),
(2, 4, 8),
(3, 5, 7),
(4, 5, 9),
(5, 5, 8);