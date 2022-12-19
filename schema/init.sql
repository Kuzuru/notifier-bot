CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY NOT NULL UNIQUE,
    tg_id      INT                NOT NULL UNIQUE,
    created_at TIMESTAMPTZ        NOT NULL DEFAULT now()
);

INSERT INTO users (tg_id) VALUES (1);

CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY NOT NULL UNIQUE,
    owner_id    INT                NOT NULL,
    description VARCHAR(2048)      NOT NULL,
    created_at  TIMESTAMPTZ        NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ        NOT NULL DEFAULT now(),
    CONSTRAINT fk_author FOREIGN KEY (owner_id) REFERENCES users (tg_id)
);

CREATE TABLE IF NOT EXISTS notifiers
(
    id         SERIAL PRIMARY KEY NOT NULL UNIQUE,
    task_id    INT                NOT NULL,
    notify_at  TIMESTAMPTZ        NOT NULL,
    created_at TIMESTAMPTZ        NOT NULL DEFAULT now(),
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks (id)
);