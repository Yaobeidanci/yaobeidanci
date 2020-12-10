DROP TABLE IF EXISTS user;
CREATE TABLE user (
    username varchar(50) primary key,
    password varchar(50),
    uid varchar(50),
    phone varchar(20),
    nickname varchar(50),
    header varchar(50)
);
