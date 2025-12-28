package it.kariera.api.model;

import java.time.LocalDate;

public class UserExamResult {
    private Integer id;
    private Integer userId;
    private Integer courseExamId;
    private Integer grade;
    private String status;
    private Boolean isSelected;
    private String teacher;
    private LocalDate registrationDate;

    public UserExamResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseExamId() {
        return courseExamId;
    }

    public void setCourseExamId(Integer courseExamId) {
        this.courseExamId = courseExamId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
