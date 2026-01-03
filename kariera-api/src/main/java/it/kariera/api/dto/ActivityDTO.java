package it.kariera.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) per trasferire i dati delle attività tra frontend e backend.
 *
 * DIFFERENZA CON Activity:
 * - Activity è il modello completo usato internamente (include userId)
 * - ActivityDTO è la versione "pubblica" inviata al frontend (NON include userId per sicurezza)
 *
 * Il userId viene preso dalla sessione HTTP, non dal body della richiesta!
 */
public class ActivityDTO {

    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String courseName;
    private String room;
    private String professor;

    // Costruttore vuoto
    public ActivityDTO() {}

    // Costruttore completo (utile per creare DTO velocemente)
    public ActivityDTO(Integer id, LocalTime startTime, LocalTime endTime,
                       LocalDate date, String courseName, String room, String professor) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.courseName = courseName;
        this.room = room;
        this.professor = professor;
    }

    // ==================== GETTERS E SETTERS ====================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
