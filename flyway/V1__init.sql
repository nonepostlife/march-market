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

-- auth
create table users (
                       id         bigserial primary key,
                       username   varchar(36) not null,
                       password   varchar(80) not null,
                       email      varchar(50) unique,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

create table roles (
                       id         bigserial primary key,
                       name       varchar(50) not null,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

create table users_roles (
                             user_id    bigint not null references users (id),
                             role_id    bigint not null references roles (id),
                             created_at timestamp default current_timestamp,
                             updated_at timestamp default current_timestamp,
                             primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);