package com.mospan.railway.service;

import com.mospan.railway.dao.impl.UserDaoImpl;
import com.mospan.railway.dao.interfaces.UserDao;
import com.mospan.railway.model.User;

import java.util.Collection;

public class UserService {
    UserDao dao = new UserDaoImpl();

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
    public String getEmailSenderData() {
        return dao.getEmailSenderData();
    }
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
