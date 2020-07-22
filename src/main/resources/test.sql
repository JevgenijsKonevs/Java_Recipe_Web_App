insert into users (id, password, username) values ('1', '$2y$11$Yy2cchSoRsjGLjwPyCnSUuyAFXFHyvHKTr24j1aSBd8C5y2vcr/au', 'TestUsername')

insert into user_roles (user_id, role) values ('1', 'USER')
insert into user_roles (user_id, role) values ('1', 'ADMIN')

insert into recipe (id, file_name, recipe_body, title, user_id) values ('1', 'testFilename.png', 'TestRecipeBody', 'TestTitle', '1')

insert into recipe_like (id, recipe_like, user_id, recipe_id) values ('1', 'true', '1', '1')

insert into recipe_comment (id, recipe_comment, user_id, recipe_id) values ('1', 'Test recipe comment!', '1', '1')