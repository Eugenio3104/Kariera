package it.kariera.api.dao;

import java.util.List;

/**
 Interfaccia generica che definisce le operazioni CRUD (Create, Read, Update, Delete).
 <T> Rappresenta l'oggetto Model (es. User)
 <K> Rappresenta il tipo della chiave primaria (es. Integer o String)
 */

public interface DAO<T,K> {
        T get(K id);
        List<T> getAll();
        void save(T t);
        void update(T t);
        void delete(K id);

}
