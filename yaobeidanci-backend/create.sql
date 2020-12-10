DROP TABLE IF EXISTS user;
CREATE TABLE user (
    username varchar(50) primary key,
    password varchar(50),
    uid varchar(50),
    phone varchar(20),
    nickname varchar(50),
    header varchar(255)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book (
    book_id varchar(50) primary key,
    name varchar(50),
    size integer,
    num integer,
    img varchar(255)
);