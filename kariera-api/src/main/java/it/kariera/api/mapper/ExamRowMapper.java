package it.kariera.api.mapper;

import it.kariera.api.model.Exam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ExamRowMapper implements RowMapper<Exam> {

    @Override
    public Exam mapRow(ResultSet rs, int row) throws SQLException {
        Exam exam = new Exam();
        exam.setId(rs.getInt("id"));
        exam.setName(rs.getString("name"));
        exam.setDescription(rs.getString("description"));
        exam.setCfu(rs.getInt("cfu"));
        exam.setProfessor(rs.getString("professor"));
        exam.setGrade(rs.getInt("grade"));
        exam.setStatus(rs.getString("status"));

        // Gestione della data che può essere NULL
        Date registrationDate = rs.getDate("registration_date");
        if (registrationDate != null) {
            exam.setRegistrationDate(registrationDate.toLocalDate());
        }

        return exam;
    }
}
