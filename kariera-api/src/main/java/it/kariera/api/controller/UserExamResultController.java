package it.kariera.api.controller;

import it.kariera.api.dto.ElectiveExamDTO;
import it.kariera.api.dto.UserExamDTO;
import it.kariera.api.model.UserExamResult;
import it.kariera.api.service.UserExamResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-exam-results")
public class UserExamResultController {

    private final UserExamResultService userExamResultService;

    public UserExamResultController(UserExamResultService userExamResultService) {
        this.userExamResultService = userExamResultService;
    }

    // Recupera tutti i risultati
    @GetMapping
    public ResponseEntity<List<UserExamResult>> getAllUserExamResults() {
        return ResponseEntity.ok(userExamResultService.getAllUserExamResults());
    }

    // Recupera un risultato per ID
    @GetMapping("/{id}")
    public ResponseEntity<UserExamResult> getUserExamResultById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userExamResultService.getUserExamResultById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Recupera tutti i risultati di un utente
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<UserExamResult>> getUserExamResultsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userExamResultService.getUserExamResultsByUserId(userId));
    }

    // Recupera i risultati con dettagli
    @GetMapping("/by-user/{userId}/details")
    public ResponseEntity<List<UserExamDTO>> getUserExamDTOsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userExamResultService.getUserExamDTOsByUserId(userId));
    }

    // Recupera solo i risultati selezionati dall'utente
    @GetMapping("/by-user/{userId}/selected")
    public ResponseEntity<List<UserExamResult>> getUserExamResultsSelectedByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userExamResultService.getUserExamResultsSelectedByUserId(userId));
    }

    // Crea un nuovo risultato
    @PostMapping
    public ResponseEntity<Void> createUserExamResult(@RequestBody UserExamResult userExamResult) {
        userExamResultService.createUserExamResult(userExamResult);
        return ResponseEntity.ok().build();
    }

    // Aggiorna un risultato
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserExamResult(@PathVariable Integer id,
            @RequestBody UserExamResult userExamResult) {
        userExamResult.setId(id);
        userExamResultService.updateUserExamResult(userExamResult);
        return ResponseEntity.ok().build();
    }

    // Aggiorna la selezione degli esami a scelta
    @PutMapping("/elective-selection")
    public ResponseEntity<Void> updateElectiveSelection(@RequestBody ElectiveExamDTO electiveExamDTO) {
        userExamResultService.updateElectiveSelection(electiveExamDTO);
        return ResponseEntity.ok().build();
    }

    // Elimina un risultato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserExamResult(@PathVariable Integer id) {
        userExamResultService.deleteUserExamResult(id);
        return ResponseEntity.ok().build();
    }
}
