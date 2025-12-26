package it.kariera.api.mapper;


import it.kariera.api.model.UserCourseSelection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCourseRowMapper implements RowMapper<UserCourseSelection>{
    @Override
    public UserCourseSelection mapRow(ResultSet rs , int row) throws SQLException {
        UserCourseSelection userCourseSelection = new UserCourseSelection();
        userCourseSelection.setId(rs.getInt("id"));
        userCourseSelection.setUser_id(rs.getInt("user_id"));
        userCourseSelection.setCourse_id(rs.getInt("course_id"));
        return userCourseSelection;
    }
}
