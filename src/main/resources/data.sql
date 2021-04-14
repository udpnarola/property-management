/*insert default roles*/
INSERT IGNORE INTO role(id, name)VALUES(1, 'user');
INSERT IGNORE INTO role(id, name)VALUES(2, 'admin');
INSERT IGNORE INTO role(id, name)VALUES(3, 'landlord');

/*insert default users*/
INSERT IGNORE INTO user(id, api_key, first_name, last_name)
VALUES (1, 'hdgjshbe123456nnddnd', 'John', 'Doe');

INSERT IGNORE INTO user(id, api_key, first_name, last_name)
VALUES (2, 'jijne82njddj29nd9nsn', 'Administrator', 'Administrator');

INSERT IGNORE INTO user(id, api_key, first_name, last_name)
VALUES (3, 'kjkxskxje44j3djnadke', 'Land Lord', 'Land Lord');

/*insert default user roles*/
INSERT IGNORE INTO user_role(user_id, role_id)VALUES(1,1);
INSERT IGNORE INTO user_role(user_id, role_id)VALUES(2,2);
INSERT IGNORE INTO user_role(user_id, role_id)VALUES(3,3);

