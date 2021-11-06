--liquibase formatted sql

--changeset aanakireev: init

create table users
(
    id bigserial constraint users_pk primary key,
    login varchar(32),
    pass varchar(32)
);
create table roles
(
    id bigserial constraint roles_pk primary key,
    roleName varchar(32)
);
create table user_role
(
    user_id bigserial not null,
    role_id bigserial not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users,
    foreign key (role_id) references roles
);


