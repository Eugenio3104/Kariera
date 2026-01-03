
CREATE TABLE IF NOT EXISTS activities (
    id SERIAL PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    date DATE NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    room VARCHAR(100),
    professor VARCHAR(255),
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
