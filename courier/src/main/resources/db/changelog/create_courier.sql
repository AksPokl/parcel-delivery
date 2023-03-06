--liquibase formatted sql
--changeset create_courier_sql:1

CREATE TABLE IF NOT EXISTS couriers
(
    id                  uuid      NOT NULL,
    username            VARCHAR   NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    current_delivery_id uuid,
    current_status      VARCHAR   NOT NULL,
    primary key (id)
);




