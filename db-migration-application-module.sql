-- ============================================================
-- Application / Module hierarchy schema (Long/BIGSERIAL IDs)
-- Safe to run on a fresh DB. If applications/modules already exist
-- from an earlier UUID-based attempt, drop them first:
--   DROP TABLE IF EXISTS modules CASCADE;
--   DROP TABLE IF EXISTS applications CASCADE;
-- ============================================================

CREATE TABLE IF NOT EXISTS applications (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS modules (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    application_id BIGINT NOT NULL REFERENCES applications(id),
    parent_module_id BIGINT REFERENCES modules(id),
    path VARCHAR(2000) NOT NULL DEFAULT '',
    depth INTEGER NOT NULL DEFAULT 0,
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_modules_path ON modules (path text_pattern_ops);
CREATE INDEX IF NOT EXISTS idx_modules_application_id ON modules (application_id);
CREATE INDEX IF NOT EXISTS idx_modules_parent_module_id ON modules (parent_module_id);

-- test_cases: add the new module_id column alongside the legacy test_suite_id.
-- Nullable for now - see the TestSuite retirement checklist before dropping
-- test_suite_id.
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS module_id BIGINT REFERENCES modules(id);
CREATE INDEX IF NOT EXISTS idx_test_cases_module_id ON test_cases (module_id);
