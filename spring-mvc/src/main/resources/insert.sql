--liquibase formatted sql

--changeset aanakireev:1

insert into users(id, login, pass)
values (1, 'admin', 'admin'),
       (2, 'user', 'user');
insert into roles(id, roleName)
values (1, 'ADMIN'),
       (2, 'USER');
insert into user_role(user_id, role_id)
values (1, 1),
       (2, 2);