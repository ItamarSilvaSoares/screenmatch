SELECT 'CREATE DATABASE alura_series'
    WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'alura_series'
)\gexec