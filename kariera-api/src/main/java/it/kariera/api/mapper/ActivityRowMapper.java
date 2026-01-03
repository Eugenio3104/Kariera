package it.kariera.api.mapper;

import it.kariera.api.model.Activity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MAPPER che converte le righe del database (ResultSet) in oggetti Activity.
 *
 * COME FUNZIONA:
 * 1. JdbcTemplate esegue una query SQL (es. SELECT * FROM activities)
 * 2. Il database ritorna un ResultSet (come una tabella Excel)
 * 3. Questo mapper prende ogni riga e crea un oggetto Activity
 *
 * È simile a UserRowMapper che hai già.
 */
public class ActivityRowMapper implements RowMapper<Activity> {

    /**
     * Metodo chiamato automaticamente da JdbcTemplate per ogni riga del risultato.
     *
     * @param rs Il ResultSet contenente i dati della riga corrente
     * @param rowNum Numero della riga (non lo usiamo, ma Spring lo richiede)
     * @return Un oggetto Activity popolato con i dati della riga
     */
    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getInt("id"));
        activity.setStartTime(rs.getTime("start_time").toLocalTime());
        activity.setEndTime(rs.getTime("end_time").toLocalTime());
        activity.setDate(rs.getDate("date").toLocalDate());
        activity.setCourseName(rs.getString("course_name"));
        activity.setRoom(rs.getString("room"));
        activity.setProfessor(rs.getString("professor"));
        activity.setUserId(rs.getInt("user_id"));

        return activity;
    }
}
