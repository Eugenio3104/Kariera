package it.kariera.api.dao;

import it.kariera.api.mapper.ExamRowMapper;
import it.kariera.api.model.Exam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamDAOImp implements DAO<Exam, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final ExamRowMapper examRowMapper = new ExamRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM exam";
    private static final String SELECT_BY_ID = "SELECT * FROM exam WHERE id = ?";
    private static final String INSERT = "INSERT INTO exam (name, description, cfu, professor, grade, status, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE exam SET name = ?, description = ?, cfu = ?, professor = ?, grade = ?, status = ?, registration_date = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM exam WHERE id = ?";

    public ExamDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Exam get(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, examRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Exam> getAll() {
        return jdbcTemplate.query(SELECT_ALL, examRowMapper);
    }

    @Override
    public void save(Exam exam) {
        jdbcTemplate.update(INSERT,
                exam.getName(),
                exam.getDescription(),
                exam.getCfu(),
                exam.getProfessor(),
                exam.getGrade(),
                exam.getStatus(),
                exam.getRegistrationDate()
        );
    }

    @Override
    public void update(Exam exam) {
        jdbcTemplate.update(UPDATE,
                exam.getName(),
                exam.getDescription(),
                exam.getCfu(),
                exam.getProfessor(),
                exam.getGrade(),
                exam.getStatus(),
                exam.getRegistrationDate(),
                exam.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }
}
