package it.kariera.api.mapper;

import it.kariera.api.model.CourseExam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseExamRowMapper implements RowMapper<CourseExam> {

    @Override
    public CourseExam mapRow(ResultSet rs, int row) throws SQLException {
        CourseExam courseExam = new CourseExam();
        courseExam.setId(rs.getInt("id"));
        courseExam.setCourseId(rs.getInt("course_id"));
        courseExam.setName(rs.getString("name"));
        courseExam.setCfu(rs.getInt("cfu"));

        Integer academicYear = rs.getObject("academic_year", Integer.class);
        courseExam.setAcademicYear(academicYear);

        courseExam.setIsElective(rs.getBoolean("is_elective"));
        return courseExam;
    }
}
