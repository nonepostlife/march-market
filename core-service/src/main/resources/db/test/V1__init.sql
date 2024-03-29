create table categories
(
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table products
(
    id              bigserial primary key,
    title           varchar(255),
    price           numeric(8, 2) not null,
    category_id     bigint references categories (id),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title) values ('Еда'), ('Ноутбуки'), ('Смартфоны');

insert into products (title, price, category_id)
values ('Молоко', 100.20, 1),
       ('Хлеб', 80.20, 1),
       ('Сыр', 90.20, 1),
       ('Масло', 320.00, 1);

create table orders
(
    id              bigserial primary key,
    username        varchar(255),
    total_price     numeric(8, 2),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table orders_items
(
    id                      bigserial primary key,
    order_id                bigint references orders (id),
    product_id              bigint references products (id),
    price_per_product       numeric(8, 2),
    quantity                int,
    price                   numeric(8, 2),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into orders (username, total_price)
values ('bob', 441.00),
       ('bob', 1280.00);

insert into orders_items (order_id, product_id, price_per_product, quantity, price)
values (1, 1, 100.20, 2, 200.40),
       (1, 2, 80.20, 3, 240.60),
       (2, 4, 320.00, 4, 1280.00);
