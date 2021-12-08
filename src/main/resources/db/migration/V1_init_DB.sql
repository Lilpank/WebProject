create sequence hibernate_sequence start 1 increment 1

create table comment
(
    id        int8 not null,
    comment   varchar(255),
    film_id   int8,
    rating_id int8,
    user_id   int8,
    primary key (id)
);

create table film
(
    id          int8 not null,
    description varchar(4096) not null,
    filename    varchar(255),
    title       varchar(255) not null,
    view        int4,
    user_id     int8,
    primary key (id)
);

create table film_comments
(
    film_id     int8 not null,
    comments_id int8 not null,
    primary key (film_id, comments_id)
);
create table film_rating
(
    film_id   int8 not null,
    rating_id int8 not null,
    primary key (film_id, rating_id)
);
create table genre_name
(
    genre_id int8 not null,
    genres   varchar(255)
);
create table rating
(
    id      int8 not null,
    value   int4,
    user_id int8,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
(
    id       int8    not null,
    active   boolean not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists film_comments add constraint UK_e6f353hpbrpktmn44t5xxbnk0 unique (comments_id);

alter table if exists film_rating add constraint UK_dk3fkvh4i800bo6cg6w7e68xy unique (rating_id);

alter table if exists comment add constraint FKb6gnv47yxa2jewd4jpvm3pnfk foreign key (film_id) references film;

alter table if exists comment add constraint FK1ycdvbhcpqly0e1hje19gp6um foreign key (rating_id) references rating;

alter table if exists comment add constraint FKgcgdcgly6u49hf4g8y2di3g4p foreign key (user_id) references usr;

alter table if exists film add constraint FK5dyd6el58ke9g8p5kbb0rye6y foreign key (user_id) references usr;

alter table if exists film_comments add constraint FK47u3bxr7w02ifso8qx9a9ish7 foreign key (comments_id) references comment;

alter table if exists film_comments add constraint FKrq1yr95bh9gs83tgqxgws5oft foreign key (film_id) references film;

alter table if exists film_rating add constraint FKfnv31oyphbs3h69m1fvqrhwwh foreign key (rating_id) references rating;

alter table if exists film_rating add constraint FKprdsa0i4frjaoojx3x4k6ong5 foreign key (film_id) references film;

alter table if exists genre_name add constraint FKkxdi7qqfqr19g7lvujdol5ses foreign key (genre_id) references film;

alter table if exists rating add constraint FKlgi9t6w691ggmgm5m5s5ebqjl foreign key (user_id) references usr;

alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;

