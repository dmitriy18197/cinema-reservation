drop table if exists SEAT;

create table SEAT (
    id       SERIAL PRIMARY KEY NOT NULL,
    status   varchar default 'VACANT'
);