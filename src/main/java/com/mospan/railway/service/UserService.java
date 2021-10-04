package com.mospan.railway.service;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.UserDao;
import com.mospan.railway.model.User;

import java.util.Collection;

public class UserService {
    Dao<User> dao = new UserDao();

    public void insert(User user) {
        dao.insert(user);
    }
    public void update(User user) {
        dao.update(user);
    }
    public User find(String login) {
        return dao.find(login);
    }
    public User findById(long id) {
        return dao.findById(id);
    }
    public void delete(User user) {
        dao.delete(user);
    }
    public Collection<User> findAll() {
        return dao.findAll();
    }
}
