package it.kariera.api.controller;

import it.kariera.api.dto.ActivityDTO;
import it.kariera.api.model.User;
import it.kariera.api.service.CalendarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/activities")
    public ResponseEntity<?> getActivities(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            List<ActivityDTO> activities = calendarService.getActivitiesByUser(user.getId());
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/activities")
    public ResponseEntity<?> createActivity(@RequestBody ActivityDTO activityDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            ActivityDTO created = calendarService.createActivity(activityDTO, user.getId());
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/activities/{id}")
    public ResponseEntity<?> updateActivity(
            @PathVariable Integer id,
            @RequestBody ActivityDTO activityDTO,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            ActivityDTO updated = calendarService.updateActivity(id, activityDTO, user.getId());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/activities/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        try {
            calendarService.deleteActivity(id, user.getId());
            return ResponseEntity.ok(Map.of("message", "Activity deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
