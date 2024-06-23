CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    userName varchar(64) UNIQUE NOT NULL,
    password varchar(64) NOT NULL,
    phone varchar(16) UNIQUE,
    active boolean DEFAULT true NOT NULL,
);