package it.kariera.api.service;

import it.kariera.api.dao.CourseExamDAOImp;
import it.kariera.api.dao.UserCourseSelectionDAOImp;
import it.kariera.api.dao.UserExamResultDAOImp;
import it.kariera.api.model.CourseExam;
import it.kariera.api.model.UserCourseSelection;
import it.kariera.api.model.UserExamResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseSelectionService {

    private final UserCourseSelectionDAOImp userCourseSelectionDAOImp;
    private final CourseExamDAOImp courseExamDAOImp;
    private final UserExamResultDAOImp userExamResultDAOImp;

    public UserCourseSelectionService(UserCourseSelectionDAOImp userCourseSelectionDAOImp,
    CourseExamDAOImp courseExamDAOImp, UserExamResultDAOImp userExamResultDAOImp) {
        this.userCourseSelectionDAOImp = userCourseSelectionDAOImp;
        this.courseExamDAOImp = courseExamDAOImp;
        this.userExamResultDAOImp = userExamResultDAOImp;
    }

    // Recupera la selezione dell'utente (può essere null se non ha ancora scelto)
    public UserCourseSelection getUserSelection(Integer userId) {
        List<UserCourseSelection> selections = userCourseSelectionDAOImp.getByUserId(userId);
        return (selections != null && !selections.isEmpty()) ? selections.get(0) : null;
    }

    // Salva o sostituisce la selezione dell'utente
    public void saveUserSelection(Integer userId, Integer courseId) {
        // Cancella eventuali selezioni precedenti
        userCourseSelectionDAOImp.deleteAllByUserId(userId);

        // Cancella eventuali risultati precedenti
        List<UserExamResult> oldResults = userExamResultDAOImp.getByUserId(userId);
        for (UserExamResult result : oldResults) {
            userExamResultDAOImp.delete(result.getId());
        }

        // Salva la nuova selezione
        UserCourseSelection selection = new UserCourseSelection();
        selection.setUser_id(userId);
        selection.setCourse_id(courseId);
        userCourseSelectionDAOImp.save(selection);
        initializeUserExamsForCourse(userId, courseId);
    }
    //Crea automaticamente i record per user_exam result per gli esami del corso
    private void initializeUserExamsForCourse(Integer userId, Integer courseId) {
        // Recupera tutti gli esami del corso (obbligatori + a scelta)
        List<CourseExam> courseExams = courseExamDAOImp.getByCourseId(courseId);
        for (CourseExam exam : courseExams) {
            UserExamResult userExam = new UserExamResult();
            userExam.setUserId(userId);
            userExam.setCourseExamId(exam.getId());
            userExam.setStatus("NOT_TAKEN");

            // Gli esami obbligatori sono selezionati di default
            // Gli esami a scelta sono deselezionati (l'utente li sceglierà dopo)
            userExam.setIsSelected(!exam.getIsElective());

            // Grade, teacher e registrationDate rimangono NULL
            userExam.setGrade(null);
            userExam.setTeacher(null);
            userExam.setRegistrationDate(null);
            // Salva il record
            userExamResultDAOImp.save(userExam);
        }
    }
}
