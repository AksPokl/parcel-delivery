--liquibase formatted sql
--changeset create_delivery_statuses_sql:1

CREATE TABLE IF NOT EXISTS delivery_statuses
(
    id          SERIAL PRIMARY KEY,
    status      VARCHAR   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    courier_id  uuid,
    delivery_id uuid      NOT NULL,
    CONSTRAINT fk_delivery
        FOREIGN KEY (delivery_id)
            REFERENCES deliveries (id)
);
