package it.kariera.api.service;

import it.kariera.api.dao.UserCourseSelectionDAOImp;
import it.kariera.api.model.UserCourseSelection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseSelectionService {

    private final UserCourseSelectionDAOImp userCourseSelectionDAOImp;

    public UserCourseSelectionService(UserCourseSelectionDAOImp dao) {
        this.userCourseSelectionDAOImp = dao;
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

        // Salva la nuova selezione
        UserCourseSelection selection = new UserCourseSelection();
        selection.setUser_id(userId);
        selection.setCourse_id(courseId);
        userCourseSelectionDAOImp.save(selection);
    }
}
