DROP SCHEMA IF EXISTS social_network_db ;
CREATE SCHEMA IF NOT EXISTS social_network_db;

CREATE TABLE USER (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NULL,
  first_name VARCHAR(255) NULL,
  last_name VARCHAR(255) NULL,
  password VARCHAR(255) NULL
);

CREATE TABLE POST(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  body VARCHAR(2000) NULL,
  created DATETIME NULL,
  imageurl VARCHAR(255) NULL,
  user_id BIGINT NOT NULL,

  CONSTRAINT post_user_id
    FOREIGN KEY (user_id)
    REFERENCES user (id)
);

CREATE TABLE COMMENT(
  id BIGINT  AUTO_INCREMENT PRIMARY KEY ,
  content VARCHAR(255) NULL,
  created DATETIME NULL,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,

  CONSTRAINT comment_post_id
    FOREIGN KEY (post_id)
    REFERENCES post(id),
  CONSTRAINT comment_user_id
    FOREIGN KEY (user_id)
    REFERENCES user(id)
);

