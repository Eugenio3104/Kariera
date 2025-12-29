package it.kariera.api.service;

import it.kariera.api.dao.CourseExamDAOImp;
import it.kariera.api.model.CourseExam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseExamService {

    private final CourseExamDAOImp courseExamDAOImp;

    public CourseExamService(CourseExamDAOImp courseExamDAOImp) {
        this.courseExamDAOImp = courseExamDAOImp;
    }

    //Recupera tutti gli esami
    public List<CourseExam> getAllCourseExams(){
        return courseExamDAOImp.getAll();
    }

    //Recupera un esame di un corso dall'id specificato
    public CourseExam getExamById(Integer id){
        CourseExam exam = courseExamDAOImp.get(id);
        if(exam == null){
            throw new RuntimeException("Exam not found");
        }
        return exam;
    }

    //Recupera tutti gli esami di un corso specificato
    public List<CourseExam> getExamsByCourseId(Integer courseId){
        return courseExamDAOImp.getByCourseId(courseId);
    }

    //Recupera tutti gli esami obbligatori di un corso specificato
    public List<CourseExam> getMandatoryExamsByCourseId(Integer courseId){
        return courseExamDAOImp.getMandatoryByCourseId(courseId);
    }

    //Recupera tutti gli esami a scelta di un corso specificato
    public List<CourseExam> getElectiveExamsByCourseId(Integer courseId){
        return courseExamDAOImp.getElectiveByCourseId(courseId);
    }

    //crea un nuovo esame di un corso specificato
    public void createCourseExam(CourseExam courseExam) {
        if (courseExam.getName() == null || courseExam.getCourseId() == null) {
            throw new RuntimeException("Name and CourseId are required");
        }
        courseExamDAOImp.save(courseExam);
    }

    //modifica un esame di un corso specificato
    public void updateCourseExam(CourseExam courseExam) {
        if (courseExamDAOImp.get(courseExam.getId()) == null) {
            throw new RuntimeException("Exam not found with id: " + courseExam.getId());
        }
        courseExamDAOImp.update(courseExam);
    }
    //elimina un esame di un corso specificato
    public void deleteCourseExam(Integer id) {
        if (courseExamDAOImp.get(id) == null) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        courseExamDAOImp.delete(id);
    }

}
