package com.mospan.railway.service;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.StationDao;
import com.mospan.railway.dao.UserDao;
import com.mospan.railway.model.Station;
import com.mospan.railway.model.User;

import java.util.Collection;

public class StationService {
    Dao<Station> dao = new StationDao();

    public void insert(Station station) {
        dao.insert(station);
    }
    public void update(long id, Station station) {
        dao.update(station);
    }
    public Station find(String name) {
        return dao.find(name);
    }
    public Station findById(long id) {
        return dao.findById(id);
    }
    public void delete(Station station) {
        dao.delete(station);
    }
    public Collection<Station> findAll() {
        return dao.findAll();
    }
}
