INSERT IGNORE INTO users(username,password,enabled) VALUES ('priya','priya', true);
INSERT IGNORE INTO users(username,password,enabled) VALUES ('naveen','naveen', true);
INSERT IGNORE INTO user_roles (username, role) VALUES ('priya', 'ROLE_USER');
INSERT IGNORE INTO user_roles (username, role) VALUES ('priya', 'ROLE_ADMIN');
INSERT IGNORE INTO user_roles (username, role) VALUES ('naveen', 'ROLE_USER');