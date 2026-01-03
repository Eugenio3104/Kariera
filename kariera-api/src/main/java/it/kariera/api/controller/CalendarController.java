package it.kariera.api.controller;

import it.kariera.api.dto.ActivityDTO;
import it.kariera.api.model.User;
import it.kariera.api.service.CalendarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER REST per gestire le API del calendario.
 *
 * RESPONSABILITÀ:
 * - Definire gli endpoint HTTP (GET, POST, PUT, DELETE)
 * - Gestire autenticazione (verificare che l'utente sia loggato)
 * - Chiamare il Service per eseguire le operazioni
 * - Ritornare le risposte HTTP appropriate (200, 400, 401)
 *
 * BASE URL: /api/calendar
 */
@RestController // Dice a Spring: "Questa classe gestisce richieste HTTP e ritorna JSON"
@RequestMapping("/api/calendar") // Tutte le rotte iniziano con /api/calendar
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {

    // Riferimento al Service per la logica di business
    private final CalendarService calendarService;

    // Constructor Injection
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * GET /api/calendar/activities
     *
     * Recupera tutte le attività dell'utente loggato.
     *
     * FLUSSO:
     * 1. Prende l'utente dalla sessione HTTP
     * 2. Se non loggato -> errore 401
     * 3. Chiama il Service per recuperare le attività
     * 4. Ritorna la lista in formato JSON
     *
     * @param session La sessione HTTP (iniettata automaticamente da Spring)
     * @return ResponseEntity con la lista di attività (200) o errore (401/400)
     */
    @GetMapping("/activities")
    public ResponseEntity<?> getActivities(HttpSession session) {
        // Prende l'utente dalla sessione (impostato durante il login)
        User user = (User) session.getAttribute("user");

        // CONTROLLO AUTENTICAZIONE
        if (user == null) {
            // Utente non loggato -> errore 401 Unauthorized
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            // Recupera le attività tramite il Service
            List<ActivityDTO> activities = calendarService.getActivitiesByUser(user.getId());

            // Ritorna status 200 OK con la lista in JSON
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            // Qualcosa è andato storto -> errore 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * POST /api/calendar/activities
     *
     * Crea una nuova attività.
     *
     * BODY RICHIESTA (JSON):
     * {
     *   "startTime": "09:00:00",
     *   "endTime": "11:00:00",
     *   "date": "2025-12-30",
     *   "courseName": "Matematica",
     *   "room": "A12",
     *   "professor": "Mario Rossi"
     * }
     *
     * @param activityDTO I dati della nuova attività (convertiti automaticamente da JSON)
     * @param session La sessione HTTP
     * @return ResponseEntity con l'attività creata (200) o errore (401/400)
     */
    @PostMapping("/activities")
    public ResponseEntity<?> createActivity(@RequestBody ActivityDTO activityDTO, HttpSession session) {
        // Controllo autenticazione
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            // Chiama il Service per creare l'attività
            // NOTA: userId viene preso dalla sessione, NON dal body!
            ActivityDTO created = calendarService.createActivity(activityDTO, user.getId());

            // Ritorna l'attività creata
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            // Errore di validazione (es. ora fine < ora inizio)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Altri errori
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /api/calendar/activities/{id}
     *
     * Modifica un'attività esistente.
     *
     * ESEMPIO URL: PUT /api/calendar/activities/5
     *
     * @param id ID dell'attività da modificare (preso dall'URL)
     * @param activityDTO Nuovi dati dell'attività
     * @param session La sessione HTTP
     * @return ResponseEntity con l'attività aggiornata (200) o errore
     */
    @PutMapping("/activities/{id}")
    public ResponseEntity<?> updateActivity(
            @PathVariable Integer id,  // @PathVariable prende {id} dall'URL
            @RequestBody ActivityDTO activityDTO,
            HttpSession session) {

        // Controllo autenticazione
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            // Chiama il Service per aggiornare
            ActivityDTO updated = calendarService.updateActivity(id, activityDTO, user.getId());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            // Errore di validazione
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Altri errori (es. attività non trovata, non autorizzato)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /api/calendar/activities/{id}
     *
     * Elimina un'attività.
     *
     * ESEMPIO URL: DELETE /api/calendar/activities/5
     *
     * @param id ID dell'attività da eliminare
     * @param session La sessione HTTP
     * @return ResponseEntity con messaggio di successo (200) o errore
     */
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, HttpSession session) {
        // Controllo autenticazione
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            // Chiama il Service per eliminare
            calendarService.deleteActivity(id, user.getId());

            // Ritorna messaggio di successo
            return ResponseEntity.ok("Activity deleted successfully");
        } catch (Exception e) {
            // Errore (es. attività non trovata)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
