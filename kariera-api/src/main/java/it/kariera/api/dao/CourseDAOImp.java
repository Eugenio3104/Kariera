package it.kariera.api.dao;

import it.kariera.api.mapper.CourseRowMapper;
import it.kariera.api.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAOImp implements DAO<Course,Integer>{


    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper courseRowMapper = new CourseRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM courses";
    private static final String SELECT_BY_ID = "SELECT * FROM courses WHERE id = ?";
    private static final String SELECT_BY_STUDY_AREA = "SELECT * FROM courses WHERE study_area_id = ?";


    public CourseDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Course get(Integer id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_ID, courseRowMapper, id);
        }catch(Exception e){return null;}
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SELECT_ALL, courseRowMapper);
    }

    public List<Course> getAllByStudyArea(Integer studyAreaId){
        return jdbcTemplate.query(SELECT_BY_STUDY_AREA, courseRowMapper, studyAreaId);
    }

    @Override
    public void save(Course course) {
    //da implementare in seguito
    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Integer id) {
    //da implementare in seguito
    }
}
