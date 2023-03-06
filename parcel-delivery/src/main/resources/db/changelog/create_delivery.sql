--liquibase formatted sql
--changeset create_delivery_sql:1

CREATE TABLE IF NOT EXISTS deliveries
(
    id           uuid      NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    delivered_at TIMESTAMP,
    courier_id   uuid,
    parcel_id    uuid      NOT NULL,
    country      VARCHAR   NOT NULL,
    city         VARCHAR   NOT NULL,
    address      VARCHAR   NOT NULL,
    last_status  VARCHAR   NOT NULL,

    primary key (id),
    CONSTRAINT fk_parcel
        FOREIGN KEY (parcel_id)
            REFERENCES parcels (id)
);


