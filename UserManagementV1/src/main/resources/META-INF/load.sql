INSERT INTO ROLES(rolename) VALUES ('ADMIN');
INSERT INTO ROLES(rolename) VALUES ('MAKER');
INSERT INTO ROLES(rolename) VALUES ('APPROVER');
INSERT INTO ROLES(rolename) VALUES ('ANONYMOUS');

INSERT INTO USERS(sso_id, password, first_name, last_name, email, account_non_locked, enabled, failed_attempts) VALUES ('sam','$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Sam','Smith','samy@xyz.com', 1, 1, 0);

INSERT INTO USERS(sso_id, password, first_name, last_name, email, account_non_locked, enabled, failed_attempts) VALUES ('narendra','$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Narendra','Korrapati','narendra@xyz.com', 1, 1, 0);

insert into roles_users values(1, 1);
insert into roles_users values(1, 2);
insert into roles_users values(2, 2);

insert into URLS(URL_NAME) VALUES ('/');
insert into URLS(URL_NAME) VALUES ('/login');
insert into URLS(URL_NAME) VALUES ('/welcome');
insert into URLS(URL_NAME) VALUES ('/static/css/bootstrap.css');
insert into URLS(URL_NAME) VALUES ('/static/css/app.css');
insert into URLS(URL_NAME) VALUES ('/login?error');
insert into URLS(URL_NAME) VALUES ('/logout');
insert into URLS(URL_NAME) VALUES ('/login?logout');
insert into URLS(URL_NAME) VALUES ('/dashboard');
insert into URLS(URL_NAME) VALUES ('/login-error');
insert into URLS(URL_NAME) VALUES ('/static/images/BSS.png');
insert into URLS(URL_NAME) VALUES ('/static/js/jquery.min.js.download');
insert into URLS(URL_NAME) VALUES ('/listUsers');
insert into urls(url_name) values ('#');

insert into ROLES_URLS(role_id, url_id) values (1, 1);
insert into ROLES_URLS(role_id, url_id) values (1, 2);
insert into ROLES_URLS(role_id, url_id) values (1, 3);
insert into ROLES_URLS(role_id, url_id) values (1, 4);
insert into ROLES_URLS(role_id, url_id) values (1, 5);
insert into ROLES_URLS(role_id, url_id) values (1, 6);
insert into ROLES_URLS(role_id, url_id) values (1, 7);
insert into ROLES_URLS(role_id, url_id) values (1, 8);
insert into ROLES_URLS(role_id, url_id) values (1, 9);
insert into ROLES_URLS(role_id, url_id) values (1, 10);
insert into ROLES_URLS(role_id, url_id) values (1, 11);
insert into ROLES_URLS(role_id, url_id) values (1, 12);
insert into ROLES_URLS(role_id, url_id) values (1, 13);
insert into ROLES_URLS(role_id, url_id) values (1, 14);

insert into ROLES_URLS(role_id, url_id) values (2, 1);
insert into ROLES_URLS(role_id, url_id) values (2, 2);
insert into ROLES_URLS(role_id, url_id) values (2, 3);
insert into ROLES_URLS(role_id, url_id) values (2, 4);
insert into ROLES_URLS(role_id, url_id) values (2, 5);
insert into ROLES_URLS(role_id, url_id) values (2, 6);
insert into ROLES_URLS(role_id, url_id) values (2, 7);
insert into ROLES_URLS(role_id, url_id) values (2, 8);
insert into ROLES_URLS(role_id, url_id) values (2, 9);
insert into ROLES_URLS(role_id, url_id) values (2, 10);
insert into ROLES_URLS(role_id, url_id) values (2, 11);
insert into ROLES_URLS(role_id, url_id) values (2, 12);
insert into ROLES_URLS(role_id, url_id) values (2, 14);

insert into ROLES_URLS(role_id, url_id) values (4, 1);
insert into ROLES_URLS(role_id, url_id) values (4, 2);
insert into ROLES_URLS(role_id, url_id) values (4, 3);
insert into ROLES_URLS(role_id, url_id) values (4, 4);
insert into ROLES_URLS(role_id, url_id) values (4, 5);
insert into ROLES_URLS(role_id, url_id) values (4, 6);
insert into ROLES_URLS(role_id, url_id) values (4, 7);
insert into ROLES_URLS(role_id, url_id) values (4, 8);
insert into ROLES_URLS(role_id, url_id) values (4, 10);
insert into ROLES_URLS(role_id, url_id) values (4, 11);
insert into ROLES_URLS(role_id, url_id) values (4, 12);

insert into menus values(1, 'Home', 'M', 1, 1);
insert into menus values(2, 'DashBoard', 'M', 2, 9);
insert into menus values(3, 'Admin', 'M', 3, 14);
insert into menus values(4, 'User Management', 'S', 3, 13);

insert into roles_menus values(1, 1);
insert into roles_menus values(2, 1);
insert into roles_menus values(3, 1);
insert into roles_menus values(4, 1);
