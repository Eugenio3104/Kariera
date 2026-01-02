package it.kariera.api.dao;

import it.kariera.api.dto.UserExamDTO;
import it.kariera.api.model.UserExamResult;
import it.kariera.api.mapper.UserExamResultRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserExamResultDAOImp implements DAO<UserExamResult, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final UserExamResultRowMapper mapper = new UserExamResultRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM user_exam_results";
    private static final String SELECT_BY_ID = "SELECT * FROM user_exam_results WHERE id = ?";
    private static final String SELECT_BY_USER = "SELECT * FROM user_exam_results WHERE user_id = ?";
    private static final String SELECT_BY_USER_SELECTED = "SELECT * FROM user_exam_results WHERE user_id = ? AND is_selected = true";
    private static final String INSERT = "INSERT INTO user_exam_results (user_id, course_exam_id, grade, status, is_selected, registration_date, teacher) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE user_exam_results SET grade = ?, status = ?, is_selected = ?, registration_date = ?, teacher = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user_exam_results WHERE id = ?";
    private static final String DELETE_BY_USER = "DELETE FROM user_exam_results WHERE user_id = ?";
    private static final String SELECT_USER_EXAM_DTO = "SELECT uer.id, ce.name as exam_name, ce.cfu, ce.academic_year, ce.is_elective, " +
            "uer.grade, uer.status, uer.registration_date, uer.teacher, uer.is_selected " +
            "FROM user_exam_results uer " +
            "JOIN course_exams ce ON uer.course_exam_id = ce.id " +
            "WHERE uer.user_id = ?";
    private static final String SELECT_EXAMS_COUNTS = "SELECT COUNT(*) FROM user_exam_results WHERE user_id = ?";
    private static final String SELECT_EXAM_COUNTS_PASSED = "SELECT COUNT(*) FROM user_exam_results WHERE user_id = ? AND status = 'PASSED'";
    private static final String SELECT_AVERAGE_GRADE = "SELECT AVG(grade) FROM user_exam_results WHERE user_id = ? AND status = 'PASSED'";

    private static final String SELECT_TOTAL_CFU = "SELECT SUM(ce.cfu) FROM user_exam_results uer" +
            " JOIN course_exams ce ON uer.course_exam_id = ce.id" +
            " WHERE uer.user_id = ?";

    private static final String SELECT_ACQUIRED_CFU = "SELECT SUM(ce.cfu) FROM user_exam_results uer " +
            "JOIN course_exams ce ON uer.course_exam_id = ce.id" +
            " WHERE uer.user_id = ? AND uer.status = 'PASSED'";

    private static final String SELECT_WEIGHTED_SUM = "SELECT SUM(ce.cfu * uer.grade) FROM user_exam_results uer " +
            "JOIN course_exams ce ON uer.course_exam_id = ce.id" +
            " WHERE uer.user_id = ? AND uer.status = 'PASSED'";

    public UserExamResultDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserExamResult get(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, mapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserExamResult> getAll() {
        return jdbcTemplate.query(SELECT_ALL, mapper);
    }

    @Override
    public void save(UserExamResult result) {
        jdbcTemplate.update(INSERT, result.getUserId(), result.getCourseExamId(), result.getGrade(), result.getStatus(),
                result.getIsSelected(), result.getRegistrationDate(), result.getTeacher());
    }

    @Override
    public void update(UserExamResult result) {
        jdbcTemplate.update(UPDATE, result.getGrade(), result.getStatus(), result.getIsSelected(),
                result.getRegistrationDate(), result.getTeacher(), result.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<UserExamResult> getByUserId(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_USER, mapper, userId);
    }

    public List<UserExamResult> getSelectedByUserId(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_USER_SELECTED, mapper, userId);
    }

    public void deleteByUserId(Integer userId) {
        jdbcTemplate.update(DELETE_BY_USER, userId);
    }

    public List<UserExamDTO> getUserExamDTOsByUserId(Integer userId) {
        return jdbcTemplate.query(SELECT_USER_EXAM_DTO, (rs, rowNum) -> {
            UserExamDTO dto = new UserExamDTO();
            dto.setId(rs.getInt("id"));
            dto.setExamName(rs.getString("exam_name"));
            dto.setCfu(rs.getInt("cfu"));
            dto.setAcademicYear(rs.getInt("academic_year"));
            dto.setIsElective(rs.getBoolean("is_elective"));

            // Gestione NULL per grade
            Integer grade = rs.getObject("grade", Integer.class);
            dto.setGrade(grade);

            dto.setStatus(rs.getString("status"));

            // Gestione NULL per registration_date
            java.sql.Date date = rs.getDate("registration_date");
            dto.setRegistrationDate(date != null ? date.toLocalDate() : null);

            dto.setTeacher(rs.getString("teacher"));
            dto.setIsSelected(rs.getBoolean("is_selected"));
            return dto;
        }, userId);
    }

    public Integer getExamCount(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_EXAMS_COUNTS, Integer.class, userId);
    }

    public Integer getExamCountPassed(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_EXAM_COUNTS_PASSED, Integer.class, userId);
    }

    public Double getAverageGrade(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_AVERAGE_GRADE, Double.class, userId);
    }

    public Integer getTotalCFU(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_TOTAL_CFU, Integer.class, userId);
    }

    public Integer getAcquiredCFU(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_ACQUIRED_CFU, Integer.class, userId);
    }

    public Double getWeightedSum(Integer userId) {
        return jdbcTemplate.queryForObject(SELECT_WEIGHTED_SUM, Double.class, userId);
    }
}
