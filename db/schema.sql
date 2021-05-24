CREATE TABLE if not exists post (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    photo TEXT
);




