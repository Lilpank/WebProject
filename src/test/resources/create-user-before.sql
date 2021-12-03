delete from user_role where user_id=10;
delete from usr where username = 'dru';
delete from user_role where user_id=20;
delete from usr where username = 'mike';


insert into usr(id, username, password, active)
values (10, 'dru', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', true),
       (20, 'mike', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', true);

insert into user_role(user_id, roles)
values (10, 'ADMIN'),
       (10, 'USER'),
       (20, 'USER');