create sequence address_SEQ start with 1 increment by 1;
create sequence phone_SEQ start with 1 increment by 1;

create table address
(
    id   bigint not null primary key,
    street varchar(255)
);

create table phone
(
    id   bigint not null primary key,
    number varchar(11),
    client_id bigint,
    foreign key(client_id) REFERENCES client (id)
);

alter table client add column address_id bigint;

ALTER TABLE client ADD CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address (id);

