package it.kariera.api.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/*
  Questa classe gestisce la sicurezza delle password.
  Si occupa di trasformare le password in chiaro in hash prima di salvarle nel DB
  */
@Component
public class PasswordUtils {

    /*Prende la password inserita dall'utente e la cripta.*/
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /*Metodo che ci permette di confrontare la password inserita dall'utente con quella salvata nel DB. */
    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}