--liquibase formatted sql
--changeset create_uuid_extension_sql:1

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";