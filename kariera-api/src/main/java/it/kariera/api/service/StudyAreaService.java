package it.kariera.api.service;

import it.kariera.api.dao.CourseDAOImp;
import it.kariera.api.dao.StudyAreaDAOImp;
import it.kariera.api.dto.StudyAreaCoursesDTO;
import it.kariera.api.model.Course;
import it.kariera.api.model.StudyArea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyAreaService {

    private final StudyAreaDAOImp studyAreaDAOImp;
    private final CourseDAOImp courseDAOImp;

    public StudyAreaService(StudyAreaDAOImp studyAreaDAOImp, CourseDAOImp courseDAOImp) {
        this.studyAreaDAOImp = studyAreaDAOImp;
        this.courseDAOImp = courseDAOImp;
    }

    // Recupera tutte le aree di studio
    public List<StudyArea> getAllStudyAreas() {
        return studyAreaDAOImp.getAll();
    }

    // Recupera un'area di studio specificata dall'id
    public StudyArea getStudyAreaById(Integer id) {
        StudyArea studyArea = studyAreaDAOImp.get(id);
        if (studyArea == null) {
            throw new RuntimeException("Study area not found");
        }
        return studyArea;
    }

    // recupera tutte le aree di studio con i relativi corsi associati
    public List<StudyAreaCoursesDTO> getAllStudyAreaWithCourses() {
        // Prima query: recupera tutte le aree di studio
        List<StudyArea> studyAreas = studyAreaDAOImp.getAll();
        List<StudyAreaCoursesDTO> result = new ArrayList<>();

        // Per ogni area, recupera i corsi associati
        for (StudyArea sa : studyAreas) {
            List<Course> courses = courseDAOImp.getAllByStudyArea(sa.getId());
            StudyAreaCoursesDTO dto = new StudyAreaCoursesDTO(sa.getId(), sa.getName(), courses);
            result.add(dto);
        }

        return result;
    }
}
