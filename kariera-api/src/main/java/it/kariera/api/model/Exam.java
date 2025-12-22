package it.kariera.api.model;

import java.time.LocalDate;

public class Exam {

    private int id;
    private int grade;
    private String status;
    private LocalDate registrationDate;
    private String description;
    private String name;
    private int cfu;
    private String professor;

    public Exam() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) {this.registrationDate = registrationDate;}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCfu() { return cfu; }
    public void setCfu(int cfu) { this.cfu = cfu; }

    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }
}
