INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('User1');
INSERT INTO client(name) VALUES ('User2');
INSERT INTO client(name) VALUES ('User3');
INSERT INTO client(name) VALUES ('User4');

INSERT INTO loans(game_id, client_id, fecha_ini, fecha_fin) VALUES (1, 1, '2025-01-06', '2025-01-19');
INSERT INTO loans(game_id, client_id, fecha_ini, fecha_fin) VALUES (2, 1, '2025-01-06', '2025-01-19');
INSERT INTO loans(game_id, client_id, fecha_ini, fecha_fin) VALUES (2, 2, '2025-01-20', '2025-02-02');
INSERT INTO loans(game_id, client_id, fecha_ini, fecha_fin) VALUES (3, 3, '2025-02-01', '2025-02-14');
INSERT INTO loans(game_id, client_id, fecha_ini, fecha_fin) VALUES (4, 4, '2025-02-15', '2025-02-28');