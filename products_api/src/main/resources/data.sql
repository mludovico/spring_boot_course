create table if not exists product (
    id varchar(255) not null primary key,
    name varchar(50) not null,
    description varchar(255),
    price numeric(18, 2)
);