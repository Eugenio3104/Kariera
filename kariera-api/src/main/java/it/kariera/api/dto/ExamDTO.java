package it.kariera.api.dto;

import java.time.LocalDate;

public class ExamDTO {

    private Integer id;
    private int grade;
    private String status;
    private LocalDate registrationDate;
    private String description;
    private String name;
    private int cfu;
    private String professor;

    public ExamDTO() {}

    public ExamDTO(Integer id, String name, String description, int cfu, String professor,
                   int grade, String status, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cfu = cfu;
        this.professor = professor;
        this.grade = grade;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCfu() { return cfu; }
    public void setCfu(int cfu) { this.cfu = cfu; }

    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }
}
