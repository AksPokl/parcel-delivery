--liquibase formatted sql
--changeset create_courier_sql:1

CREATE TABLE IF NOT EXISTS parcels
(
    id            uuid      NOT NULL,
    weight        INT       NOT NULL,
    user_id       uuid      NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    delivery_date DATE      NOT NULL,
    primary key (id)
);


