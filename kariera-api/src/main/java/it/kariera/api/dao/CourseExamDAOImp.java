package it.kariera.api.dao;

import it.kariera.api.model.CourseExam;
import it.kariera.api.mapper.CourseExamRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseExamDAOImp implements DAO<CourseExam, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final CourseExamRowMapper mapper = new CourseExamRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM course_exams";
    private static final String SELECT_BY_ID = "SELECT * FROM course_exams WHERE id = ?";
    private static final String SELECT_BY_COURSE = "SELECT * FROM course_exams WHERE course_id = ?";
    private static final String SELECT_MANDATORY = "SELECT * FROM course_exams WHERE course_id = ? AND is_elective = false ORDER BY academic_year, name";
    private static final String SELECT_ELECTIVE = "SELECT * FROM course_exams WHERE course_id = ? AND is_elective = true ORDER BY name";
    private static final String INSERT = "INSERT INTO course_exams (course_id, name, cfu, academic_year, is_elective) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE course_exams SET course_id = ?, name = ?, cfu = ?, academic_year = ?, is_elective = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM course_exams WHERE id = ?";

    public CourseExamDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CourseExam get(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, mapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CourseExam> getAll() {
        return jdbcTemplate.query(SELECT_ALL, mapper);
    }

    @Override
    public void save(CourseExam exam) {
        jdbcTemplate.update(INSERT, exam.getCourseId(), exam.getName(), exam.getCfu(), exam.getAcademicYear(),
                exam.getIsElective());
    }

    @Override
    public void update(CourseExam exam) {
        jdbcTemplate.update(UPDATE, exam.getCourseId(), exam.getName(), exam.getCfu(), exam.getAcademicYear(),
                exam.getIsElective(), exam.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<CourseExam> getByCourseId(Integer courseId) {
        return jdbcTemplate.query(SELECT_BY_COURSE, mapper, courseId);
    }

    public List<CourseExam> getMandatoryByCourseId(Integer courseId) {
        return jdbcTemplate.query(SELECT_MANDATORY, mapper, courseId);
    }

    public List<CourseExam> getElectiveByCourseId(Integer courseId) {
        return jdbcTemplate.query(SELECT_ELECTIVE, mapper, courseId);
    }
}
