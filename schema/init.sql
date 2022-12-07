CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY NOT NULL UNIQUE,
    name       VARCHAR(255)       NOT NULL,
    created_at timestamptz        NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY NOT NULL UNIQUE,
    owner_id    INT                NOT NULL,
    description VARCHAR(2048)     NOT NULL,
    created_at  TIMESTAMPTZ        NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ        NOT NULL DEFAULT now(),
    CONSTRAINT fk_author FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS notifiers
(
    task_id    INT         NOT NULL,
    notify_at  timestamptz NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks (id)
);