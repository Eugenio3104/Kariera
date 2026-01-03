package it.kariera.api.dao;

import it.kariera.api.mapper.ActivityRowMapper;
import it.kariera.api.model.Activity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO (Data Access Object) per gestire le operazioni CRUD sulle attività.
 *
 * RESPONSABILITÀ:
 * - Eseguire query SQL sul database
 * - Inserire, aggiornare, eliminare e recuperare attività
 * - NON contiene logica di business (quella sta nel Service)
 *
 * Implementa l'interfaccia generica DAO<Activity, Integer>
 */
@Repository // Dice a Spring: "Questa è una classe che accede al database"
public class ActivityDAOImp implements DAO<Activity, Integer> {

    // JdbcTemplate è lo strumento di Spring per eseguire query SQL
    private final JdbcTemplate jdbcTemplate;

    // Mapper per convertire ResultSet -> Activity
    private final ActivityRowMapper activityRowMapper = new ActivityRowMapper();

    // ==================== QUERY SQL PREDEFINITE ====================
    // Le scriviamo come costanti per evitare errori di battitura

    // Recupera tutte le attività (usata raramente)
    private static final String SELECT_ALL = "SELECT * FROM activities";

    // Recupera un'attività per ID
    private static final String SELECT_BY_ID = "SELECT * FROM activities WHERE id = ?";

    // Recupera tutte le attività di un utente (ORDINANDO per data e ora)
    private static final String SELECT_BY_USER =
            "SELECT * FROM activities WHERE user_id = ? ORDER BY date, start_time";

    // Inserisce una nuova attività
    private static final String INSERT =
            "INSERT INTO activities (start_time, end_time, date, course_name, room, professor, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // Aggiorna un'attività esistente (controlla anche user_id per sicurezza)
    private static final String UPDATE =
            "UPDATE activities SET start_time = ?, end_time = ?, date = ?, course_name = ?, " +
                    "room = ?, professor = ? WHERE id = ? AND user_id = ?";

    // Elimina un'attività (controlla anche user_id per sicurezza)
    private static final String DELETE = "DELETE FROM activities WHERE id = ? AND user_id = ?";

    // Constructor Injection (Spring inietta automaticamente JdbcTemplate)
    public ActivityDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera un'attività per ID.
     *
     * @param id ID dell'attività da recuperare
     * @return L'attività trovata, oppure null se non esiste
     */
    @Override
    public Activity get(Integer id) {
        try {
            // queryForObject ritorna UN SOLO oggetto
            // Se non trova nulla, lancia un'eccezione -> la catturiamo e ritorniamo null
            return jdbcTemplate.queryForObject(SELECT_BY_ID, activityRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Recupera TUTTE le attività nel database (senza filtri).
     *
     * @return Lista di tutte le attività
     */
    @Override
    public List<Activity> getAll() {
        // query ritorna una LISTA di oggetti
        return jdbcTemplate.query(SELECT_ALL, activityRowMapper);
    }

    /**
     * Recupera tutte le attività di uno specifico utente.
     * IMPORTANTE: Le attività sono ordinate per data e ora di inizio.
     *
     * @param userId ID dell'utente
     * @return Lista delle attività dell'utente, ordinata cronologicamente
     */
    public List<Activity> getByUserId(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_USER, activityRowMapper, userId);
    }

    /**
     * Salva una nuova attività nel database.
     * L'ID viene generato automaticamente dal database (SERIAL).
     *
     * @param activity L'attività da salvare (con tutti i campi tranne id)
     */
    @Override
    public void save(Activity activity) {
        // update esegue una query INSERT/UPDATE/DELETE
        // I "?" vengono sostituiti dai parametri in ordine
        jdbcTemplate.update(INSERT,
                activity.getStartTime(),      // ? numero 1
                activity.getEndTime(),        // ? numero 2
                activity.getDate(),           // ? numero 3
                activity.getCourseName(),     // ? numero 4
                activity.getRoom(),           // ? numero 5
                activity.getProfessor(),      // ? numero 6
                activity.getUserId()          // ? numero 7
        );
    }

    /**
     * Aggiorna un'attività esistente.
     *
     * SICUREZZA: La WHERE clause include anche user_id.
     * Questo previene che un utente modifichi le attività di altri utenti.
     *
     * @param activity L'attività con i nuovi dati (deve avere id e userId)
     */
    @Override
    public void update(Activity activity) {
        jdbcTemplate.update(UPDATE,
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getDate(),
                activity.getCourseName(),
                activity.getRoom(),
                activity.getProfessor(),
                activity.getId(),       // WHERE id = ?
                activity.getUserId()    // AND user_id = ? (IMPORTANTE per sicurezza)
        );
    }

    /**
     * Elimina un'attività per ID (senza controllo utente).
     *
     * @param id ID dell'attività da eliminare
     */
    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM activities WHERE id = ?", id);
    }

    /**
     * Elimina un'attività controllando anche l'utente proprietario.
     *
     * SICUREZZA: Previene che un utente elimini le attività di altri.
     *
     * @param id ID dell'attività
     * @param userId ID dell'utente proprietario
     */
    public void deleteByIdAndUser(Integer id, Integer userId) {
        jdbcTemplate.update(DELETE, id, userId);
    }
}
