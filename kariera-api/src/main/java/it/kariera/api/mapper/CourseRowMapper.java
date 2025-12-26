package it.kariera.api.mapper;


import it.kariera.api.model.Course;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs , int row) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        course.setStudy_area_id(rs.getInt("study_area_id"));
        return course;
    }
}
