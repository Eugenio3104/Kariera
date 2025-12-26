CREATE TABLE IF NOT EXISTS courses(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    study_area_id INT NOT NULL,
    FOREIGN KEY (study_area_id) REFERENCES study_areas(id)
);