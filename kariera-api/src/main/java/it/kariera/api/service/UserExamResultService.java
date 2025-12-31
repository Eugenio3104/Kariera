package it.kariera.api.service;

import it.kariera.api.dao.UserExamResultDAOImp;
import it.kariera.api.dto.ElectiveExamDTO;
import it.kariera.api.dto.UserExamDTO;
import it.kariera.api.model.UserExamResult;
import org.springframework.stereotype.Service;
import it.kariera.api.dto.UserStatsDTO;

import java.util.List;

@Service
public class UserExamResultService {

    private final UserExamResultDAOImp userExamResultDAOImp;

    public UserExamResultService(UserExamResultDAOImp userExamResultDAOImp) {
        this.userExamResultDAOImp = userExamResultDAOImp;
    }

    //Recupera tutti i risultati degli esami
    public List<UserExamResult> getAllUserExamResults() {
        return userExamResultDAOImp.getAll();
    }

    //Recupera un risultato di un esame specificato dall'id'
    public UserExamResult getUserExamResultById(Integer id) {
        UserExamResult result = userExamResultDAOImp.get(id);
        if(result == null) {
            throw new RuntimeException("Result not found");
        }
        return result;
    }

    //Recupera tutti i risultati di un utente specificato
    public List<UserExamResult> getUserExamResultsByUserId(Integer userId) {
        return userExamResultDAOImp.getByUserId(userId);
    }

    //Recupera i risultati con dettagli esami (usando join) come UserExamDTO
    public List<UserExamDTO> getUserExamDTOsByUserId(Integer userId) {
        return userExamResultDAOImp.getUserExamDTOsByUserId(userId);
    }

    //Recupera solo i risultati selezionati dall'utente
    public List<UserExamResult> getUserExamResultsSelectedByUserId(Integer userId) {
        return userExamResultDAOImp.getSelectedByUserId(userId);
    }

    //crea un risultato
    public void createUserExamResult(UserExamResult userExamResult) {
        userExamResultDAOImp.save(userExamResult);
    }

    //aggiorna un risultato
    public void updateUserExamResult(UserExamResult userExamResult) {
        userExamResultDAOImp.update(userExamResult);
    }

    //aggiorna la selezione degli esami a scelta
    public void updateElectiveSelection(ElectiveExamDTO electiveExamDTO) {
        Integer userId = electiveExamDTO.getUserId();
        List<Integer> selectedExamIds = electiveExamDTO.getElectiveExamIds();

        // Recupera tutti i risultati dell'utente
        List<UserExamResult> allResults = userExamResultDAOImp.getByUserId(userId);

        // Per ogni risultato, aggiorna is_selected
        for (UserExamResult result : allResults) {
            boolean isSelected = selectedExamIds.contains(result.getCourseExamId());
            result.setIsSelected(isSelected);
            userExamResultDAOImp.update(result);
        }
    }

    //elimina un risultato
    public void deleteUserExamResult(Integer id) {
        userExamResultDAOImp.delete(id);
    }

    public UserStatsDTO getUserStats(Integer userId) {
        UserStatsDTO stats = new UserStatsDTO();

        stats.setTotalExams(userExamResultDAOImp.getExamCount(userId)); //prendo il numero di tutti gli esami del corso in questione

        stats.setPassedExams(userExamResultDAOImp.getExamCountPassed(userId)); //prendo il numero di esami passati del corso

        stats.setArithmeticAverage(userExamResultDAOImp.getAverageGrade(userId)); //media aritmetica degli esami

        stats.setTotalCFU(userExamResultDAOImp.getTotalCFU(userId)); //totale CFU del corso

        stats.setAcquiredCFU(userExamResultDAOImp.getAcquiredCFU(userId)); //totale dei cfu acquisiti dall'utente

        Double weightSum = userExamResultDAOImp.getWeightedSum(userId);
        Integer acquiredCfu = userExamResultDAOImp.getAcquiredCFU(userId);

        if (acquiredCfu != null && acquiredCfu > 0 && weightSum != null) {
            stats.setWeightedAverage(weightSum / acquiredCfu); //calcolo della media ponderata
        } else {
            stats.setWeightedAverage(0.0);
        }

        //calcolo del voto di laurea
        if (stats.getWeightedAverage() != null) {
            stats.setDegreeGradePrediction(stats.getWeightedAverage() * 110 / 30);
        } else {
            stats.setDegreeGradePrediction(0.0);
        }

        return stats;
    }


}