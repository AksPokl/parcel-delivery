--liquibase formatted sql
--changeset naksenova:1
CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

--changeset naksenova:2
INSERT INTO roles(name)
VALUES ('ROLE_USER');
INSERT INTO roles(name)
VALUES ('ROLE_COURIER');
INSERT INTO roles(name)
VALUES ('ROLE_ADMIN');

--changeset naksenova:3
CREATE TABLE IF NOT EXISTS users
(
    id       uuid,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

--changeset naksenova:4
INSERT INTO users(id, username, email, password)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'admin', 'admin@emil.com',
        '$2a$10$7BaOCDMZUstL12P4dmaXr.8oqagVZJXK0btik60KZrtYIh/6fNVPe');

--changeset naksenova:5
CREATE TABLE IF NOT EXISTS user_roles
(
    id      SERIAL PRIMARY KEY,
    user_id uuid   NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);

--changeset naksenova:6
INSERT INTO user_roles(user_id, role_id)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 3);






