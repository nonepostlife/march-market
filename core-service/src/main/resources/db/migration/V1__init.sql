create table categories (
    id          bigserial primary key,
    title       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table products (
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8, 2),
    category_id bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories (title) values
('Food'),
('Electronic');

insert into products (title, price, category_id)
values ('Bread', 32.00, 1),
       ('Milk', 120.00, 1),
       ('Butter', 320.00, 1),
       ('Cheese', 500.00, 1),
       ('Apple', 100.00, 1),
       ('Banana', 140.00, 1),
       ('Orange', 150.00, 1),
       ('Potato', 77.00, 1),
       ('Cucumber', 105.00, 1),
       ('Meat', 480.00, 1),
       ('Tomato', 170.00, 1),
       ('Fish', 320.00, 1),
       ('Sugar', 50.00, 1);

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
    id                      bigserial,
    order_id                bigint not null references orders (id),
    product_id              bigint not null references products (id),
    price_per_product       numeric(8, 2),
    quantity                int,
    price                   numeric(8, 2),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp,
    primary key (id, order_id)
);

create table orders_details
(
    id              bigserial primary key,
    order_id        bigint not null references orders (id),
    address         varchar(255),
    phone           varchar(15),
    add_info        varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);