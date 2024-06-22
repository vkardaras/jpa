CREATE TABLE IF NOT EXISTS authors(
 id INTEGER NOT NULL PRIMARY KEY,
 name TEXT
 );


INSERT INTO authors (id, name) VALUES (1, 'Laur Spilca');
INSERT INTO authors (id, name) VALUES (2, 'Mark Heckler');
INSERT INTO authors (id, name) VALUES (3, 'Thomas Vitale');

CREATE TABLE IF NOT EXISTS books(
 id INTEGER NOT NULL PRIMARY KEY,
 title TEXT
 );


INSERT INTO books (id, title) VALUES (1, 'Spring Security in Action');
INSERT INTO books (id, title) VALUES (2, 'Spring Start Here');
INSERT INTO books (id, title) VALUES (3, 'Troubleshooting Java');
INSERT INTO books (id, title) VALUES (4, 'Spring Boot Up & Running');
INSERT INTO books (id, title) VALUES (5, 'Cloud Native Spring in Action');

CREATE TABLE IF NOT EXISTS book_shops(
 id INTEGER NOT NULL PRIMARY KEY,
 name TEXT
 );

INSERT INTO book_shops (id, name) VALUES (1, 'Manning');
INSERT INTO book_shops (id, name) VALUES (2, 'O Reilly');
INSERT INTO book_shops (id, name) VALUES (3, 'Elephant');

CREATE TABLE IF NOT EXISTS authors_books(
 authors_id INTEGER NOT NULL REFERENCES authors(id),
 books_id INTEGER NOT NULL REFERENCES books(id),
 PRIMARY KEY(authors_id, books_id)
 );

INSERT INTO authors_books (authors_id, books_id) VALUES (1, 1);
INSERT INTO authors_books (authors_id, books_id) VALUES (1, 2);
INSERT INTO authors_books (authors_id, books_id) VALUES (1, 3);
INSERT INTO authors_books (authors_id, books_id) VALUES (2, 4);
INSERT INTO authors_books (authors_id, books_id) VALUES (3, 5);

CREATE TABLE IF NOT EXISTS book_shops_books(
 bookShops_id INTEGER NOT NULL REFERENCES book_shops(id),
 books_id INTEGER NOT NULL REFERENCES books(id),
 PRIMARY KEY(bookShops_id, books_id)
 );

INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (1, 1);
INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (2, 1);
INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (3, 1);
INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (1, 2);
INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (2, 2);
INSERT INTO book_shops_books (bookShops_id, books_id) VALUES (3, 2);