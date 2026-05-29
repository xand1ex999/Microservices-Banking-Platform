CREATE TABLE transactions (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id  UUID NOT NULL REFERENCES accounts(id),
    amount      NUMERIC(19, 4) NOT NULL,
    currency    VARCHAR(10) NOT NULL,
    type        VARCHAR(20),
    status      VARCHAR(20),
    created_at  TIMESTAMPTZ NOT NULL
);
