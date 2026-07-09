-- ============================================================
-- Idempotent schema-sync migration for Testify
-- Generated from the current JPA entity definitions.
-- Safe to run multiple times: only ADDs missing columns,
-- never drops or alters existing ones, so it won't touch data.
--
-- NOTE: new columns are added as nullable regardless of the
-- entity's @Column(nullable = false), since forcing NOT NULL on
-- an existing populated table would fail without a backfill.
-- Tighten constraints manually after confirming data is populated.
-- ============================================================

-- users
ALTER TABLE users ADD COLUMN IF NOT EXISTS username VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS password VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS first_name VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS last_name VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(50);
ALTER TABLE users ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT TRUE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- test_suites
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS name VARCHAR(255);
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS created_by UUID;
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS updated_by UUID;
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE test_suites ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- test_cases
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS name VARCHAR(255);
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS preconditions TEXT;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS steps TEXT;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS expected_result TEXT;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS priority VARCHAR(50);
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS status VARCHAR(50);
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS test_suite_id BIGINT;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS created_by UUID;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- test_plans  (this is the one that just broke)
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS name VARCHAR(255);
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS scope TEXT;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS start_date DATE;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS end_date DATE;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS status VARCHAR(50);
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS created_by UUID;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE test_plans ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- test_plan_test_cases (join table for the ManyToMany)
CREATE TABLE IF NOT EXISTS test_plan_test_cases (
    test_plan_id BIGINT NOT NULL,
    test_case_id BIGINT NOT NULL,
    PRIMARY KEY (test_plan_id, test_case_id)
);

-- test_runs
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS name VARCHAR(255);
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS build_version VARCHAR(255);
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS environment VARCHAR(255);
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS status VARCHAR(50);
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS execution_start_date TIMESTAMP;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS execution_end_date TIMESTAMP;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS total_test_count INTEGER DEFAULT 0;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS passed_count INTEGER DEFAULT 0;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS failed_count INTEGER DEFAULT 0;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS blocked_count INTEGER DEFAULT 0;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS test_plan_id BIGINT;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS created_by UUID;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE test_runs ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- test_executions
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS test_run_id BIGINT;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS test_case_id BIGINT;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS status VARCHAR(50);
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS actual_result TEXT;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS comments TEXT;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS executed_by UUID;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS executed_at TIMESTAMP;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE test_executions ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- bugs
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS title VARCHAR(255);
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS severity VARCHAR(50);
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS priority VARCHAR(50);
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS status VARCHAR(50);
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS steps_to_reproduce TEXT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS expected_behavior TEXT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS actual_behavior TEXT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS resolution TEXT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS environment VARCHAR(255);
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS target_fix_date DATE;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS resolved_date DATE;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS test_case_id BIGINT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS test_run_id BIGINT;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS assigned_to UUID;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS reported_by UUID;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE bugs ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
