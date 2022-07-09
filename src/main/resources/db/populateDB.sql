TRUNCATE TABLE user_roles;
TRUNCATE TABLE meals;
TRUNCATE TABLE users CASCADE;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO public.meals (id, user_id, datetime, description, calories)
VALUES (100004, 100000, '2022-07-06 08:00:00.000000', 'Завтрак', 500),
       (100005, 100000, '2022-07-06 12:00:00.000000', 'Обед', 700),
       (100006, 100000, '2022-07-06 21:00:00.000000', 'Ужин', 800),
       (100007, 100001, '2022-07-06 08:00:00.000000', 'Завтрак', 500),
       (100008, 100001, '2022-07-06 12:00:00.000000', 'Обед', 700),
       (100009, 100001, '2022-07-06 21:00:00.000000', 'Ужин', 800),
       (100010, 100001, '2022-07-07 08:00:00.000000', 'Завтрак на след. день', 500),
       (100011, 100001, '2022-07-05 13:00:00.000000', 'Обед предыдущего дня', 700);
