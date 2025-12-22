package it.kariera.api.service;

import it.kariera.api.dao.UserDAOImp;
import it.kariera.api.model.User;
import it.kariera.api.utils.PasswordUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAOImp userDAOImp;
    private final PasswordUtils passwordUtils;

    public AuthService(UserDAOImp userDAOImp, PasswordUtils passwordUtils) {
        this.userDAOImp = userDAOImp;
        this.passwordUtils = passwordUtils;
    }

    public User login(String email, String password){
        User user = userDAOImp.findByEmail(email);
        if(user!= null && passwordUtils.verifyPassword(password, user.getPassword())){
            return user;
        }
        else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public User register(User user){
        if(userDAOImp.findByEmail(user.getEmail()) == null){
            userDAOImp.save(user);
            return userDAOImp.findByEmail(user.getEmail());
        }else{
            throw new RuntimeException("Email already exists");
        }
    }
}
