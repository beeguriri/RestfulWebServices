INSERT INTO users(id, name, join_date, password, ssn) VALUES (90001, 'user1', now(), 'test1111', '801212-1111111');
INSERT INTO users(id, name, join_date, password, ssn) VALUES (90002, 'user2', now(), 'test2222', '900808-2222222');
INSERT INTO users(id, name, join_date, password, ssn) VALUES (90003, 'user3', now(), 'test3333', '000101-3333333');

INSERT INTO post(description, user_id) VALUES ('my first post', 90001);
INSERT INTO post(description, user_id) VALUES ('my second post', 90001);