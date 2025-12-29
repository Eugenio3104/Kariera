package it.kariera.api.controller;

import it.kariera.api.dto.StudyAreaCoursesDTO;
import it.kariera.api.model.StudyArea;
import it.kariera.api.service.StudyAreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/study-area")
public class StudyAreaController {

    private final StudyAreaService studyAreaService;

    public StudyAreaController(StudyAreaService studyAreaService) {
        this.studyAreaService = studyAreaService;
    }

    // Recupera tutte le aree di studio
    @GetMapping
    public ResponseEntity<List<StudyArea>> getAllStudyAreas() {
        return ResponseEntity.ok(studyAreaService.getAllStudyAreas());
    }

    // Recupera un'area di studio specificata dall'id
    @GetMapping("/{id}")
    public ResponseEntity<StudyArea> getAreaById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(studyAreaService.getStudyAreaById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Recupera tutte le aree di studio con i relativi corsi
    @GetMapping("/with-courses")
    public ResponseEntity<List<StudyAreaCoursesDTO>> getAreaWithCourses() {
        return ResponseEntity.ok(studyAreaService.getAllStudyAreaWithCourses());
    }

}
