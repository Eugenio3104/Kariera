package it.kariera.api.service;

import it.kariera.api.dao.CourseDAOImp;
import it.kariera.api.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseDAOImp courseDAOImp;
    public CourseService(CourseDAOImp courseDAOImp) {
        this.courseDAOImp = courseDAOImp;
    }

    //Recupera tutti i corsi
    public List<Course> getAllCourses(){
        return courseDAOImp.getAll();
    }

    //Recupera un corso specificato dall'id
    public Course getCourseById(Integer id){
        Course course = courseDAOImp.get(id);
        if(course == null){
            throw new RuntimeException("Course not found");
        }
        return course;
    }

    //Recupera tutti i corsi di un area di studio specificata
    public List<Course> getCourseByStudyArea(Integer studyAreaId){
        return courseDAOImp.getAllByStudyArea(studyAreaId);
    }



}
