package it.kariera.api.controller;

import it.kariera.api.model.CourseExam;
import it.kariera.api.service.CourseExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-exams")
public class CourseExamController {

    private final CourseExamService courseExamService;

    public CourseExamController(CourseExamService courseExamService) {
        this.courseExamService = courseExamService;
    }

    // Recupera tutti gli esami
    @GetMapping
    public ResponseEntity<List<CourseExam>> getAllCourseExams() {
        return ResponseEntity.ok(courseExamService.getAllCourseExams());
    }

    // Recupera un esame per ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseExam> getExamById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(courseExamService.getExamById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Recupera tutti gli esami di un corso
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<CourseExam>> getExamsByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseExamService.getExamsByCourseId(courseId));
    }

    // Recupera gli esami obbligatori di un corso
    @GetMapping("/by-course/{courseId}/mandatory")
    public ResponseEntity<List<CourseExam>> getMandatoryExamsByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseExamService.getMandatoryExamsByCourseId(courseId));
    }

    // Recupera gli esami a scelta di un corso
    @GetMapping("/by-course/{courseId}/elective")
    public ResponseEntity<List<CourseExam>> getElectiveExamsByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseExamService.getElectiveExamsByCourseId(courseId));
    }

    // Crea un nuovo esame
    @PostMapping
    public ResponseEntity<Void> createCourseExam(@RequestBody CourseExam courseExam) {
        try {
            courseExamService.createCourseExam(courseExam);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Aggiorna un esame
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourseExam(@PathVariable Integer id, @RequestBody CourseExam courseExam) {
        try {
            courseExam.setId(id);
            courseExamService.updateCourseExam(courseExam);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un esame
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseExam(@PathVariable Integer id) {
        try {
            courseExamService.deleteCourseExam(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
