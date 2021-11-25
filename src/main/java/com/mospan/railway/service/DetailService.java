package com.mospan.railway.service;

import com.mospan.railway.dao.impl.DetailDaoImpl;
import com.mospan.railway.dao.interfaces.DetailDao;
import com.mospan.railway.model.Detail;

import java.util.Collection;

public class DetailService {
    DetailDao dao = new DetailDaoImpl();

    public void insert(Detail detail) {
        dao.insert(detail);
    }
    public void update(Detail detail) {
        dao.update(detail);
    }
    public Detail find(String param) {
        return dao.find(param);
    }
    public Detail findById(long id) {
        return dao.findById(id);
    }
    public void delete(Detail detail) {
        dao.delete(detail);
    }
    public Collection<Detail> findAll() {
        return dao.findAll();
    }
}
