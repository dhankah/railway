package com.mospan.railway.service;

import com.mospan.railway.dao.TrainDao;

import com.mospan.railway.model.Train;

import java.util.Collection;

public class TrainService {
    TrainDao dao = new TrainDao();

    public void insert(Train train) {
        dao.insert(train);
    }
    public void update(Train train) {
        dao.update(train);
    }
    public Train find(String number) {
        return dao.find(number);
    }
    public Train findById(long id) {
        return dao.findById(id);
    }
    public void delete(Train train) {
        dao.delete(train);
    }
    public Collection<Train> findAll() {
        return dao.findAll();
    }
    public Collection<Train> findByRouteId(long id){
        return dao.findByRouteId(id);
    }
}
