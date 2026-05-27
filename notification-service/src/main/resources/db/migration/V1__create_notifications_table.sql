CREATE TABLE notifications (
    id          UUID        NOT NULL DEFAULT gen_random_uuid(),
    user_id     BIGINT,
    email       VARCHAR(255),
    type        VARCHAR(50) NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'SENT',
    message     TEXT,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_notifications PRIMARY KEY (id)
);

CREATE INDEX idx_notifications_user_id    ON notifications (user_id);
CREATE INDEX idx_notifications_type       ON notifications (type);
CREATE INDEX idx_notifications_created_at ON notifications (created_at DESC);
