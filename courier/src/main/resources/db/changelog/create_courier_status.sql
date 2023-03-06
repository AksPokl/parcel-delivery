--liquibase formatted sql
--changeset create_courier_statuses_sql:1

DROP TABLE IF EXISTS courier_statuses;

CREATE TABLE IF NOT EXISTS courier_statuses
(
    id          SERIAL PRIMARY KEY,
    status      VARCHAR   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    courier_id  uuid      NOT NULL,
    delivery_id uuid,
    CONSTRAINT fk_status
        FOREIGN KEY (courier_id)
            REFERENCES couriers (id)
);
