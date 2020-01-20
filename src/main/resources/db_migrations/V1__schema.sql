drop table if exists SEAT;
drop table if exists HALL;
drop table if exists CINEMA;

create table CINEMA (
    id SERIAL PRIMARY KEY NOT NULL,
    name varchar
);

create table HALL (
    id SERIAL PRIMARY KEY NOT NULL,
    cinemaId integer REFERENCES cinema ON DELETE CASCADE,
    name varchar
);

create table SEAT (
    id       SERIAL PRIMARY KEY NOT NULL,
    hallId   integer REFERENCES hall ON DELETE CASCADE,
    cinemaId integer REFERENCES cinema ON DELETE CASCADE,
	status   varchar default 'VACANT'
);