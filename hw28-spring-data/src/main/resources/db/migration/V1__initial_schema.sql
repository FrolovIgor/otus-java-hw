-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
-- create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id bigserial not null primary key,
    name varchar(50)
);

-- create sequence address_SEQ start with 1 increment by 1;
-- create sequence phone_SEQ start with 1 increment by 1;

create table address
(
    id bigserial not null primary key,
    client_id bigint not null references client (id),
    street varchar(255)
);

create table phone
(
    id bigserial not null primary key,
    number varchar(11),
    client_id bigint not null REFERENCES client (id),
    order_column int not null
);