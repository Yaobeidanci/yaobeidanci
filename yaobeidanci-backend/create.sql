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

DROP TABLE IF EXISTS sentence;
CREATE TABLE sentence (
    sentence_id varchar(50),
    sentence varchar(255),
    translation varchar(255),
    origin_word varchar(50)
);

DROP TABLE IF EXISTS word;
CREATE TABLE word (
    word_id integer,
    word varchar(50),
    category varchar(50),
    phonetic_uk varchar(50),
    phonetic_us varchar(50),
    relate_words varchar(255),
    explains varchar(255),
    phrases varchar(255),
    remember_method varchar(255)
);

DROP TABLE IF EXISTS schedule;
CREATE TABLE schedule (
    uid varchar(50),
    book varchar(50),
    start_date varchar(50),
    current_progress integer
);

DROP TABLE IF EXISTS star_sentence;
CREATE TABLE star_sentence (
    uid varchar(50),
    sentence_id varchar(50)
);

DROP TABLE IF EXISTS star_word;
CREATE TABLE star_word (
    uid varchar(50),
    word_id integer,
    category varchar(50)
);