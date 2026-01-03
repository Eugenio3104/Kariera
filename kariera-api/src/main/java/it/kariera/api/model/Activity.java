package it.kariera.api.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * MODELLO che rappresenta un'attività didattica (lezione) nel calendario.
 * Questa classe corrisponde alla tabella "activities" nel database.
 *
 * Contiene tutte le informazioni di una lezione:
 * - Quando si svolge (data e orari)
 * - Dove (aula)
 * - Cosa (nome corso)
 * - Chi (professore)
 * - Di chi è (userId)
 */
public class Activity {

    private Integer id;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate date;

    private String courseName;

    private String room;


    private String professor;

    private Integer userId;

    public Activity() {}

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
