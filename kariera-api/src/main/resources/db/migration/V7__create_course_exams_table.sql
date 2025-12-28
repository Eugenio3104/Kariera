CREATE TABLE IF NOT EXISTS course_exams (
    id SERIAL PRIMARY KEY,
    course_id INTEGER NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    cfu INTEGER NOT NULL,
    academic_year INTEGER,
    is_elective BOOLEAN DEFAULT false,
    CONSTRAINT check_academic_year CHECK (academic_year IS NULL OR (academic_year > 0 AND academic_year < 6)),
    CONSTRAINT check_cfu_valid CHECK (cfu >= 3 AND cfu <= 12)
);