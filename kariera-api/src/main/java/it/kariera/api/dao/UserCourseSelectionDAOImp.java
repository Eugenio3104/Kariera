package it.kariera.api.dao;

import it.kariera.api.mapper.UserCourseRowMapper;
import it.kariera.api.model.UserCourseSelection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCourseSelectionDAOImp implements DAO<UserCourseSelection,Integer>{

    private final JdbcTemplate jdbcTemplate;
    private final UserCourseRowMapper userCourseRowMapper = new UserCourseRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM users_courses_selection";
    private static final String SELECT_BY_ID = "SELECT * FROM users_courses_selection WHERE id = ?";
    private static final String SELECT_BY_USER = "SELECT * FROM users_courses_selection WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO users_courses_selection (user_id, course_id) VALUES (?, ?)";
    private static final String DELETE_BY_USER = "DELETE FROM users_courses_selection WHERE user_id = ?";


    public UserCourseSelectionDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserCourseSelection get(Integer id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_ID, userCourseRowMapper, id);
        }catch (Exception e){return null;}
    }

    public List<UserCourseSelection> getByUserId(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_USER, userCourseRowMapper, userId);
    }


    @Override
    public List<UserCourseSelection> getAll() {
        return jdbcTemplate.query(SELECT_ALL, userCourseRowMapper);
    }

    @Override
    public void save(UserCourseSelection userCourseSelection) {
        jdbcTemplate.update(INSERT,userCourseSelection.getUser_id(),userCourseSelection.getCourse_id());
    }

    public void deleteAllByUserId(Integer userId) {
        jdbcTemplate.update(DELETE_BY_USER, userId);
    }

    @Override
    public void update(UserCourseSelection userCourseSelection) {
    //da implementare in seguito
    }

    @Override
    public void delete(Integer id) {
    //da implementare in seguito
    }
}
