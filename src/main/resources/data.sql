INSERT INTO users (username, password) VALUES ('test', '$2a$10$6gCb/kdLD6uIdPCOQKBfv.kVwpNuxIda90jzRNQiZkTK3BTKQe6Zm');

INSERT INTO links (title, url, description, created_at, updated_at, user_id, is_public) VALUES ('Google', 'https://www.google.com', 'google', NOW(), NOW(), 1, TRUE);
INSERT INTO links (title, url, description, created_at, updated_at, user_id, is_public) VALUES ('Naver', 'https://www.naver.com', 'naver', NOW(), NOW(), 1, TRUE);
INSERT INTO links (title, url, description, created_at, updated_at, user_id, is_public) VALUES ('Amazon', 'https://www.amazon.com', 'amazon', NOW(), NOW(), 1, TRUE);
INSERT INTO links (title, url, description, created_at, updated_at, user_id, is_public) VALUES ('Coupang', 'https://www.coupang.com', 'coupang', NOW(), NOW(), 1, TRUE);

INSERT INTO tags (name) VALUES ('portal');
INSERT INTO tags (name) VALUES ('shopping');
INSERT INTO tags (name) VALUES ('search engine');

INSERT INTO link_tag (link_id, tag_id) VALUES(1, 2);
INSERT INTO link_tag (link_id, tag_id) VALUES(1, 3);
INSERT INTO link_tag (link_id, tag_id) VALUES(2, 1);
INSERT INTO link_tag (link_id, tag_id) VALUES(2, 2);
INSERT INTO link_tag (link_id, tag_id) VALUES(3, 2);
INSERT INTO link_tag (link_id, tag_id) VALUES(4, 2);
