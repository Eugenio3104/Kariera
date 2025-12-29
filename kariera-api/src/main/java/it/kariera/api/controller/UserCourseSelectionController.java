package it.kariera.api.controller;

import it.kariera.api.model.UserCourseSelection;
import it.kariera.api.service.UserCourseSelectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-selections")
public class UserCourseSelectionController {

    private final UserCourseSelectionService selectionService;

    public UserCourseSelectionController(UserCourseSelectionService selectionService) {
        this.selectionService = selectionService;
    }

    // Recupera la selezione dell'utente
    @GetMapping("/{userId}")
    public ResponseEntity<UserCourseSelection> getUserSelection(@PathVariable Integer userId) {
        UserCourseSelection selection = selectionService.getUserSelection(userId);
        if (selection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(selection);
    }

    //Salva/sostituisce la selezione dell'utente
    @PostMapping
    public ResponseEntity<Void> saveUserSelection(@RequestParam Integer userId, @RequestParam Integer courseId) {
        selectionService.saveUserSelection(userId, courseId);
        return ResponseEntity.ok().build();
    }
}
