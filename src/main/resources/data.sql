insert into users (id, password, username) values ('1', '$2y$11$VLors3k49YhEtZqjDRFWuu2IwhM4lug0Tcy3.l..5420irqbzb.Ne', 'doggie')
insert into users (id, password, username) values ('2', '$2y$11$02/9T5cESVkl3v1NBLUSkeBosm.9REJqCNV.wt.IazAqX3BAgC1c6', 'potato')

insert into user_roles (user_id, role) values ('1', 'USER')
insert into user_roles (user_id, role) values ('1', 'ADMIN')
insert into user_roles (user_id, role) values ('2', 'USER')

insert into recipe (id, file_name, recipe_body, title, user_id) values ('1', 'path', 'This very yummy cake!', 'Cake', '1')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('2', 'path', 'Nommie nommie brownie!', 'Brownie', '2')

insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('1', 'true', '1', '1')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('2', 'true', '2', '1')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('3', 'true', '1', '2')

insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('1', 'uwu', '1', '1')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('2', 'owo', '2', '1')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('3', 'gib food', '1', '2')