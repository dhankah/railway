package com.mospan.railway.dao.interfaces;

import com.mospan.railway.model.Station;

import java.util.Collection;

public interface StationDao {
    Station findById(long id);
    void insert(Station station);
    void update(Station station);
    Station find(String name);
    void delete(Station station);
    Collection<Station> findAll();
    Collection<Station> findRecords(long id);
}
