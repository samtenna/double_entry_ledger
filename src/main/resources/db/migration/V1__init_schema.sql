-- accounts
CREATE TYPE account_type AS ENUM (
    'ASSET',
    'LIABILITY',
    'EQUITY',
    'REVENUE',
    'EXPENSE'
);
CREATE TYPE account_status AS ENUM (
    'ACTIVE',
    'FROZEN',
    'CLOSED'
);

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    type account_type NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status account_status NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- transactions
CREATE TYPE transaction_status AS ENUM (
    'COMPLETE',
    'PENDING',
    'FAILED'
);

CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    idempotency_key UUID NOT NULL UNIQUE,
    status transaction_status NOT NULL,
    effective_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- entries
CREATE TYPE transaction_direction AS ENUM (
    'CREDIT',
    'DEBIT'
);

CREATE TABLE entries (
    id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL REFERENCES transactions(id) ON DELETE RESTRICT,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE RESTRICT,
    amount NUMERIC(19, 4) NOT NULL CHECK (amount > 0),
    direction transaction_direction NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- indices
CREATE INDEX idx_entries_account_id ON entries(account_id);
CREATE INDEX idx_entries_transaction_id ON entries(transaction_id);
