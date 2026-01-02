package it.kariera.api.dto;

import java.time.LocalDate;

public class UserExamUpdateDTO {
    private Integer grade;
    private String teacher;
    private LocalDate registrationDate;
    private String status;

    // Getters e Setters
    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}