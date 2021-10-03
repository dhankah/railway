package com.mospan.railway.dao;


import java.sql.SQLException;
import java.util.Collection;

public interface Dao<T> {
    void insert(T entity);
    void update(long id, T entity);
    T find(String param);
    T findById(long id);
    void delete(T entity);
    Collection<T> findAll();
}
