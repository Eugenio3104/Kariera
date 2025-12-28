DROP TABLE IF EXISTS exam CASCADE;

CREATE TABLE IF NOT EXISTS user_exam_results (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    course_exam_id INT NOT NULL,
    grade INT,
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_TAKEN',
    is_selected BOOLEAN DEFAULT true,
    registration_date DATE,
    teacher VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_exam_id) REFERENCES course_exams(id) ON DELETE CASCADE,
    CONSTRAINT check_grade_valid CHECK (grade IS NULL OR (grade >= 18 AND grade <= 30)),
    CONSTRAINT check_status_valid CHECK (status IN ('NOT_TAKEN', 'PASSED', 'PREDICTED')),
    UNIQUE (user_id, course_exam_id)
);