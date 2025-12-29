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

    // ID univoco dell'attività (generato automaticamente dal database)
    private int id;

    // Ora di inizio della lezione (es. 09:00)
    private LocalTime startTime;

    // Ora di fine della lezione (es. 11:00)
    private LocalTime endTime;

    // Data in cui si svolge la lezione (es. 2025-12-30)
    private LocalDate date;

    // Nome del corso (es. "Matematica")
    private String courseName;

    // Aula dove si svolge (es. "A12")
    private String room;

    // Nome del professore che tiene la lezione
    private String professor;

    // ID dell'utente proprietario (riferimento alla tabella users)
    private int userId;

    // Costruttore vuoto (necessario per JdbcTemplate quando crea gli oggetti)
    public Activity() {}

    // ==================== GETTERS E SETTERS ====================
    // Servono per leggere e modificare i valori dei campi

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
