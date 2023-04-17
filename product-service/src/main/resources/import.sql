create sequence hibernate_sequence start 1 increment 1;

create table product
(
    id          int8 not null,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

insert into product(id, name, description) values (nextval('hibernate_sequence'), 'Product 1', 'Product One');
insert into product(id, name, description) values (nextval('hibernate_sequence'), 'Product 2', 'Product Two');
insert into product(id, name, description) values (nextval('hibernate_sequence'), 'Product 3', 'Product Three');
insert into product(id, name, description) values (nextval('hibernate_sequence'), 'Product 4', 'Product Four');
insert into product(id, name, description) values (nextval('hibernate_sequence'), 'Product 5', 'Product Five');