--liquibase formatted sql
--changeset create_message_metadata_sql:1
CREATE TABLE IF NOT EXISTS message_metadata
(
    id         uuid      NOT NULL,
    tag        INT       NOT NULL,
    created_at TIMESTAMP NOT NULL,
    primary key (id)
);
