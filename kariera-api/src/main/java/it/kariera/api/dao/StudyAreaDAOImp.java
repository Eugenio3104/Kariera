package it.kariera.api.dao;

import it.kariera.api.mapper.StudyAreaRowMapper;
import it.kariera.api.model.StudyArea;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudyAreaDAOImp implements DAO<StudyArea, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final StudyAreaRowMapper studyAreaRowMapper = new StudyAreaRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM study_areas";
    private static final String SELECT_BY_ID = "SELECT * FROM study_areas WHERE id = ?";

    public StudyAreaDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudyArea get(Integer id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_ID, studyAreaRowMapper, id);
        }catch (Exception e){return null;}
    }

    @Override
    public List<StudyArea> getAll() {
        return jdbcTemplate.query(SELECT_ALL, studyAreaRowMapper);
    }

    @Override
    public void save(StudyArea studyArea) {
    //da implementare in sequito
    }

    @Override
    public void update(StudyArea studyArea) {
    //da implementare in sequito
    }

    @Override
    public void delete(Integer id) {
    //da implementare in sequito
    }
}
