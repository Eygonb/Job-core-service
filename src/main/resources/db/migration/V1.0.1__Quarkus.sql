CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE statuses DROP CONSTRAINT pk_status CASCADE,
    ADD id uuid not null DEFAULT uuid_generate_v4 (),
    ADD CONSTRAINT pk_status PRIMARY KEY (id),
    ADD CONSTRAINT user_id_name_status_unique UNIQUE (user_id, name_status);
ALTER TABLE vacancies ADD status_id uuid,
    ADD CONSTRAINT fk_status_id FOREIGN KEY(status_id)  REFERENCES statuses(id)