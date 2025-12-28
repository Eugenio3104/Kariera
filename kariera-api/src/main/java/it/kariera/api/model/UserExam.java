package it.kariera.api.model;
import java.time.LocalDate;

public class UserExam {
    private int id;
    private int user_id;
    private int course_exam_id;
    private Integer grade;
    private String status;
    private Boolean is_selected;
    private String teacher;
    private LocalDate registrationDate;

    public UserExam(){}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getUser_id() {return user_id;}
    public void setUser_id(int user_id) {this.user_id = user_id;}

    public int getCourse_exam_id() {return course_exam_id;}
    public void setCourse_exam_id(int course_exam_id) {this.course_exam_id = course_exam_id;}

    public int getGrade() {return grade;}
    public void setGrade(int grade) {this.grade = grade;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public Boolean getIs_selected() {return is_selected;}
    public void setIs_selected(Boolean is_selected) {this.is_selected = is_selected;}

    public String getTeacher() {return teacher;}
    public void setTeacher(String teacher) {this.teacher = teacher;}

    public LocalDate getRegistrationDate() {return registrationDate;}
    public void setRegistrationDate(LocalDate  registrationDate) {this.registrationDate = registrationDate;}
}
