DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2023-01-29 11:00:00', 'User 1 Завтрак', 300),
       (100000, '2023-01-29 19:01:00', 'User 1 Ужин', 410),
       (100000, '2023-01-29 19:02:00', 'User 1 Ужин 2', 3000),
       (100000, '2023-01-30 10:00:00', 'User 1 Завтрак', 500),
       (100000, '2023-01-30 20:00:00', 'User 1 Ужин 1', 3000),
       (100000, '2023-01-30 22:10:00', 'User 1 Ужин 2', 410),
       (100000, '2023-01-31 10:00:00', 'User 1 Завтрак', 1000),
       (100000, '2023-01-31 20:00:00', 'User 1 Ужин', 410),
       (100001, '2023-01-29 01:01:00', 'Admin Ночной Перекус', 100),
       (100001, '2023-01-29 13:30:00', 'Admin Обед', 500),
       (100001, '2023-01-29 19:30:00', 'Admin Ужин', 2000),
       (100001, '2023-01-30 9:00:00', 'Admin Завтрак', 440),
       (100001, '2023-01-30 13:00:00', 'Admin Обед', 1000),
       (100001, '2023-01-31 00:00:00', 'Admin Еда на граничное значение', 100),
       (100001, '2023-01-31 13:00:00', 'Admin Обед', 3000);

