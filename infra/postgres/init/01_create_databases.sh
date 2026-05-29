#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    SELECT 'CREATE DATABASE auth_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth_db')\gexec

    SELECT 'CREATE DATABASE banking_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'banking_db')\gexec

    SELECT 'CREATE DATABASE notifications_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notifications_db')\gexec
EOSQL
