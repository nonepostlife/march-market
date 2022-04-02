create table products (
    id          bigserial primary key,
    title       varchar(255),
    price       int
);

insert into products (title, price) values
('Bread', 32),
('Milk', 120),
('Butter', 320),
('Cheese', 500);