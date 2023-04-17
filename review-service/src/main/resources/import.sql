create sequence hibernate_sequence start 1 increment 1;

create table review (
        id int8 not null,
        author varchar(255),
        created timestamp,
        objectId int8 not null,
        score int4 not null,
        text varchar(255),
        primary key (id)
);

-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User1', 'Review One', current_timestamp, 10, 1);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User2', 'Review Two', current_timestamp, 9, 1);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User3', 'Review Five', current_timestamp, 3, 2);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User1', 'Review Four', current_timestamp, 4, 3);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User4', 'Review Six', current_timestamp, 7, 3);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User2', 'Review Three', current_timestamp, 8, 1);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User5', 'Review Seven', current_timestamp, 5, 4);
-- insert into review(id,author,text,created,score,objectId) values (nextval('hibernate_sequence'), 'User3', 'Review Eight', current_timestamp, 1, 4);
