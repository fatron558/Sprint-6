--liquibase formatted sql

--changeset aanakireev:init

create table users
(
    id bigserial constraint users_pk primary key,
    login varchar(32),
    pass varchar(255)
);

create table roles
(
    id bigserial constraint roles_pk primary key,
    role_name varchar(32)
);

create table user_role
(
    user_id bigserial not null,
    role_id bigserial not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users,
    foreign key (role_id) references roles
);
create table notes
(
    id bigserial constraint notes_pk primary key,
    first_name varchar(255),
    last_name varchar(255),
    address varchar(255),
    phone varchar(255)
);


