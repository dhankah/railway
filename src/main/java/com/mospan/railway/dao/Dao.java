package com.mospan.railway.dao;


import java.util.Collection;

public interface Dao<T> {
    void insert(T entity);
    void update(long id, T entity);
    T find(String param);
    void delete(T entity);
    Collection<T> findAll();
}
