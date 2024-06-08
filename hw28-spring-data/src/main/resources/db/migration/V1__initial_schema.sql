create table client
(
    id bigserial not null primary key,
    name varchar(50)
);

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