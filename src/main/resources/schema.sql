DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
                         id          BIGINT NOT NULL auto_increment,
                         category    VARCHAR(255),
                         description VARCHAR(255),
                         name        VARCHAR(255),
                         rating      INTEGER NOT NULL,
                         PRIMARY KEY (id)
);
DROP TABLE IF EXISTS users;
create table USERS(
                         id BIGINT NOT NULL auto_increment,
                         first_name varchar(50),
                         last_name varchar(50),
                         email varchar(50),
                         username varchar(50),
                         password varchar(100),
                         verified SMALLINT CHECK (verified IN (0, 1)),
                         PRIMARY KEY (id)
);

CREATE SEQUENCE ct_email_verifications;

DROP TABLE IF EXISTS CT_EMAIL_VERIFICATIONS;
create table CT_EMAIL_VERIFICATIONS(
    verification_id varchar(50),
    username varchar(50),
    PRIMARY KEY (verification_id)
)