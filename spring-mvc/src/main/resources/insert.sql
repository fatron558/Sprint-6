--liquibase formatted sql

--changeset aanakireev:1

insert into users(id, login, pass)
values (1, 'api', '$2a$12$zR4NS.i5UOT8751vcQ9GLOzYhHverk31kX/hhdKDN3VzSAkkZoDg2'),
       (2, 'app', '$2a$12$vuTMYzE72rPBi5K4Y3m5uuBDb76thCvFc0Qhh5Y5vhAHAYOtvIi/K'),
       (3, 'admin', '$2a$12$u16jZvpFnwtghaz4kU4u1uyate/qMJNdoKIgX6FerPJevx98w9m76');
insert into roles(id, role_name)
values (1, 'ROLE_API'),
       (2, 'ROLE_APP'),
       (3, 'ROLE_ADMIN');
insert into user_role(user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3);