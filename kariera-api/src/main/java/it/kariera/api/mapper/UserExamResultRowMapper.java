package it.kariera.api.mapper;

import it.kariera.api.model.UserExamResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExamResultRowMapper implements RowMapper<UserExamResult> {

    @Override
    public UserExamResult mapRow(ResultSet rs, int row) throws SQLException {
        UserExamResult result = new UserExamResult();
        result.setId(rs.getInt("id"));
        result.setUserId(rs.getInt("user_id"));
        result.setCourseExamId(rs.getInt("course_exam_id"));

        Integer grade = rs.getObject("grade", Integer.class);
        result.setGrade(grade);

        result.setStatus(rs.getString("status"));
        result.setIsSelected(rs.getBoolean("is_selected"));
        result.setTeacher(rs.getString("teacher"));

        java.sql.Date date = rs.getDate("registration_date");
        if (date != null) {
            result.setRegistrationDate(date.toLocalDate());
        }

        return result;
    }
}
