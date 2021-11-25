package com.mospan.railway.dao.interfaces;

import com.mospan.railway.model.Detail;

import java.util.Collection;

public interface DetailDao {
    void insert(Detail detail);
    void update(Detail detail);
    Detail find(String email);
    Detail findById(long id);
    void delete(Detail detail);
    Collection<Detail> findAll();
}
