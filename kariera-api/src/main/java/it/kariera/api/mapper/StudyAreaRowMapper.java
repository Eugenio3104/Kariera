package it.kariera.api.mapper;

import it.kariera.api.model.StudyArea;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudyAreaRowMapper implements RowMapper<StudyArea> {

    @Override
    public StudyArea mapRow(ResultSet rs , int row) throws SQLException {
        StudyArea studyArea = new StudyArea();
        studyArea.setId(rs.getInt("id"));
        studyArea.setName(rs.getString("name"));
        return studyArea;
    }
}
