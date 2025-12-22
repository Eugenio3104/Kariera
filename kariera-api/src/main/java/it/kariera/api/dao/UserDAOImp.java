package it.kariera.api.dao;

import it.kariera.api.mapper.UserRowMapper;
import it.kariera.api.model.User;
import it.kariera.api.utils.PasswordUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserDAOImp implements DAO<User, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordUtils passwordUtils;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT = "INSERT INTO users (name, surname, email, password) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET name = ?, surname = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";


    public UserDAOImp(JdbcTemplate jdbcTemplate , PasswordUtils passwordUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordUtils = passwordUtils;
    }

    @Override
    public User get(Integer id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_ID,userRowMapper,id);
        }catch (Exception e){return null;}
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL,userRowMapper);
    }

    @Override
    public void save(User user) {
        String hashedPassword = passwordUtils.hashPassword(user.getPassword());
        jdbcTemplate.update(INSERT, user.getName(), user.getSurname(), user.getEmail(),hashedPassword);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE, user.getName(), user.getSurname(), user.getEmail() , user.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public User findByEmail(String email){
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_EMAIL,userRowMapper,email);
        }catch (Exception e){
            return null;
        }
    }
}
