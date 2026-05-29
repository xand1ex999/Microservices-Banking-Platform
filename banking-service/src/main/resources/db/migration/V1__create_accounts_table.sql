CREATE TABLE accounts (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     BIGINT NOT NULL,
    balance     NUMERIC(19, 4) NOT NULL,
    currency    VARCHAR(10) NOT NULL,
    status      VARCHAR(20),
    created_at  TIMESTAMPTZ
);
