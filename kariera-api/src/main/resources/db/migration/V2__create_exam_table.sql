CREATE TABLE exam (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    cfu INT NOT NULL,
    professor VARCHAR(255) NOT NULL,
    grade INT,
    status VARCHAR(50) NOT NULL,
    registration_date DATE,
    CONSTRAINT check_grade CHECK (grade >= 0 AND grade <= 30),
    CONSTRAINT check_cfu CHECK (cfu > 0)
);
