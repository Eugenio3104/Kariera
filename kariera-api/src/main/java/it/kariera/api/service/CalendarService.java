package it.kariera.api.service;

import it.kariera.api.dao.ActivityDAOImp;
import it.kariera.api.dto.ActivityDTO;
import it.kariera.api.model.Activity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//COMMENTO
/**
 * SERVICE che contiene la logica di business per la gestione del calendario.
 *
 * RESPONSABILITÀ:
 * - Validare i dati in input (es. ora fine > ora inizio)
 * - Convertire tra DTO e Entity (Model)
 * - Chiamare il DAO per le operazioni sul database
 * - Gestire le autorizzazioni (un utente può modificare solo le sue attività)
 *
 * FA DA PONTE tra Controller e DAO.
 */
@Service
public class CalendarService {

    private final ActivityDAOImp activityDAO;

    public CalendarService(ActivityDAOImp activityDAO) {
        this.activityDAO = activityDAO;
    }

    /**
     * Recupera tutte le attività di un utente.
     *
     * FLUSSO:
     * 1. Chiama il DAO per ottenere le Activity dal database
     * 2. Converte ogni Activity in ActivityDTO (per nascondere userId)
     * 3. Ritorna la lista di DTO al Controller
     *
     * @param userId ID dell'utente
     * @return Lista di ActivityDTO (senza userId)
     */
    public List<ActivityDTO> getActivitiesByUser(Integer userId) {
        List<Activity> activities = activityDAO.getByUserId(userId);

        return activities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nuova attività.
     *
     * VALIDAZIONE:
     * - Controlla che l'ora di fine sia successiva all'ora di inizio
     * - Se non valida, lancia un'eccezione (che diventerà un errore 400)
     *
     * @param activityDTO I dati della nuova attività (dal frontend)
     * @param userId ID dell'utente proprietario (dalla sessione)
     * @return Il DTO dell'attività creata
     * @throws IllegalArgumentException Se i dati non sono validi
     */
    public ActivityDTO createActivity(ActivityDTO activityDTO, Integer userId) {
        if (activityDTO.getEndTime().isBefore(activityDTO.getStartTime()) ||
                activityDTO.getEndTime().equals(activityDTO.getStartTime())) {

            throw new IllegalArgumentException("End time must be after start time");
        }

        Activity activity = convertToEntity(activityDTO);

        activity.setUserId(userId);


        activityDAO.save(activity);

        return activityDTO;
    }

    /**
     * Aggiorna un'attività esistente.
     *
     * SICUREZZA:
     * - Verifica che l'attività esista
     * - Verifica che appartenga all'utente loggato
     * - Se no, lancia un'eccezione (errore 400)
     *
     * @param activityId ID dell'attività da modificare
     * @param activityDTO Nuovi dati dell'attività
     * @param userId ID dell'utente loggato
     * @return Il DTO aggiornato
     * @throws RuntimeException Se l'attività non esiste o non appartiene all'utente
     * @throws IllegalArgumentException Se i dati non sono validi
     */
    public ActivityDTO updateActivity(Integer activityId, ActivityDTO activityDTO, Integer userId) {

        Activity existing = activityDAO.get(activityId);

        if (existing == null || existing.getUserId() != userId) {
            throw new RuntimeException("Activity not found or unauthorized");
        }

        if (activityDTO.getEndTime().isBefore(activityDTO.getStartTime()) ||
                activityDTO.getEndTime().equals(activityDTO.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Activity activity = convertToEntity(activityDTO);

        activity.setId(activityId);

        activity.setUserId(userId);

        activityDAO.update(activity);

        return activityDTO;
    }

    /**
     * Elimina un'attività.
     *
     * SICUREZZA:
     * - Come update, verifica proprietà prima di eliminare
     *
     * @param activityId ID dell'attività da eliminare
     * @param userId ID dell'utente loggato
     * @throws RuntimeException Se l'attività non esiste o non appartiene all'utente
     */
    public void deleteActivity(Integer activityId, Integer userId) {
        Activity existing = activityDAO.get(activityId);
        if (existing == null || existing.getUserId() != userId) {
            throw new RuntimeException("Activity not found or unauthorized");
        }

        activityDAO.deleteByIdAndUser(activityId, userId);
    }

    /**
     * Converte Activity (completo con userId) -> ActivityDTO (senza userId).
     *
     * Il userId NON viene incluso nel DTO per sicurezza.
     * Il frontend non ha bisogno di saperlo.
     *
     * @param activity L'oggetto Activity dal database
     * @return Un ActivityDTO pronto per essere inviato al frontend
     */
    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getId(),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getDate(),
                activity.getCourseName(),
                activity.getRoom(),
                activity.getProfessor()
        );
    }

    /**
     * Converte ActivityDTO -> Activity (senza userId, verrà aggiunto dopo).
     *
     * @param dto Il DTO ricevuto dal frontend
     * @return Un oggetto Activity (userId deve essere impostato dal chiamante)
     */
    private Activity convertToEntity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setStartTime(dto.getStartTime());
        activity.setEndTime(dto.getEndTime());
        activity.setDate(dto.getDate());
        activity.setCourseName(dto.getCourseName());
        activity.setRoom(dto.getRoom());
        activity.setProfessor(dto.getProfessor());

        return activity;
    }
}
