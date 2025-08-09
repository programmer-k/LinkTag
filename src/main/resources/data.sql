INSERT INTO users (id, username, password) VALUES (1, 'test', '$2a$10$6gCb/kdLD6uIdPCOQKBfv.kVwpNuxIda90jzRNQiZkTK3BTKQe6Zm');

INSERT INTO links (title, url, description, user_id) VALUES ('Google', 'https://www.google.com', 'google', 1);
INSERT INTO links (title, url, description, user_id) VALUES ('Naver', 'https://www.naver.com', 'naver', 1);

INSERT INTO tags (id, name) VALUES (1, 'portal');
INSERT INTO tags (id, name) VALUES (2, 'shopping');
INSERT INTO tags (id, name) VALUES (3, 'search engine');

INSERT INTO link_tag (link_id, tag_id) VALUES(1, 2);
INSERT INTO link_tag (link_id, tag_id) VALUES(1, 3);
INSERT INTO link_tag (link_id, tag_id) VALUES(2, 1);
INSERT INTO link_tag (link_id, tag_id) VALUES(2, 2);
