package it.kariera.api.controller;

import it.kariera.api.model.Course;
import it.kariera.api.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Recupera tutti i corsi
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    //  Recupera un corso per ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Recupera corsi per area di studio
    @GetMapping("/by-study-area/{studyAreaId}")
    public ResponseEntity<List<Course>> getCoursesByStudyArea(@PathVariable Integer studyAreaId) {
        return ResponseEntity.ok(courseService.getCourseByStudyArea(studyAreaId));
    }
}
