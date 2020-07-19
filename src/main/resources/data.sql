insert into users (id, password, username) values ('1', '$2y$11$VLors3k49YhEtZqjDRFWuu2IwhM4lug0Tcy3.l..5420irqbzb.Ne', 'doggie')
insert into users (id, password, username) values ('2', '$2y$11$02/9T5cESVkl3v1NBLUSkeBosm.9REJqCNV.wt.IazAqX3BAgC1c6', 'potato')
insert into users (id, password, username) values ('3', '$2y$11$cdPavU13elWqObA5MCYuD.sG/wLj2GfhZEuRuZ25Cj6Els28s.KMu', 'panda')
insert into users (id, password, username) values ('4', '$2y$11$7gP2ucv4Er2d3KwhTsIe0udOs6Wz1Rk9in4PmLywq/mtjZOo5lSua', 'cupcake')

insert into user_roles (user_id, role) values ('1', 'USER')
insert into user_roles (user_id, role) values ('1', 'ADMIN')
insert into user_roles (user_id, role) values ('2', 'USER')
insert into user_roles (user_id, role) values ('3', 'USER')
insert into user_roles (user_id, role) values ('4', 'USER')

insert into recipe (id, file_name, recipe_body, title, user_id) values ('1', 'path', 'This very yummy cake!', 'Cake', '1')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('2', 'path', 'Nommie nommie brownie!', 'Brownie', '2')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('3', 'path', 'RecipeBody3', 'RecipeTitle3', '3')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('4', 'path', 'RecipeBody4', 'RecipeTitle4', '3')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('5', 'path', 'RecipeBody5', 'RecipeTitle5', '3')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('6', 'path', 'RecipeBody6', 'RecipeTitle6', '3')
insert into recipe (id, file_name, recipe_body, title, user_id) values ('7', 'path', 'RecipeBody7', 'RecipeTitle7', '3')

insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('1', 'true', '1', '1')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('2', 'true', '2', '1')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('3', 'true', '1', '2')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('4', 'true', '1', '4')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('5', 'true', '2', '4')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('6', 'true', '3', '4')
insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('7', 'true', '4', '4')

insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('1', 'uwu', '1', '1')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('2', 'owo', '2', '1')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('3', 'gib food', '1', '2')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('4', 'gib food', '1', '4')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('5', 'gib food', '2', '4')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('6', 'gib food', '3', '4')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('7', 'gib food', '4', '4')
insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('8', 'gib food', '1', '4')