CREATE TABLE statuses
(
    name_status text not null,
    created_at timestamptz,
    modified_at timestamptz,
    user_id text not null,
    order_num integer not null,
    CONSTRAINT pk_status primary key(user_id,name_status)

);

CREATE TABLE vacancies
(
    id uuid not null,
    user_id text not null,
    name_vacancy text not null,
    created_at timestamptz,
    modified_at timestamptz,
    location_latitude float,
    location_longitude float,
    status_name text,
    company text,
    salary integer,
    notes text,
    primary key(id),
    CONSTRAINT fk_status_name FOREIGN KEY(user_id,status_name)  REFERENCES statuses(user_id,name_status)
);


CREATE TABLE events
(
    id uuid not null,
    user_id text not null,
    name text not null,
    created_at timestamptz not null,
    modified_at timestamptz not null,
    begin_date timestamptz,
    end_date timestamptz,
    notify_for integer,
    vacancy_id uuid,
    is_completed boolean default false,
    notes  text,
    primary key(id),
    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id)
);

CREATE TABLE contacts
(
    id uuid not null,
    user_id text not null,
    first_name text not null,
    last_name text not null,
    created_at timestamptz not null,
    modified_at timestamptz not null,
    location_latitude float,
    location_longitude float,
    company text,
    position text,
    mail text,
    skype text,
    telegram_id text,
    vk_id text,
    telephone text,
    vacancy_id uuid,
    notes text,
    primary key(id),
    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id)
);
