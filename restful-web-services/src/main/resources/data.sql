insert into user_details(id, birth_date, name) values(10001L, current_date(), 'Kim');
insert into user_details(id, birth_date, name) values(10002L, current_date(), 'Lee');
insert into user_details(id, birth_date, name) values(10003L, current_date(), 'Park');

insert into post(id, description, user_id) values(20001L, 'I want to learn AWS', 10001L);
insert into post(id, description, user_id) values(20002L, 'I want to learn DevOps', 10001L);

insert into post(id, description, user_id) values(20003L, 'I want to get AWS cert', 10002L);
insert into post(id, description, user_id) values(20004L, 'I want to learn Multi Cloud', 10002L);