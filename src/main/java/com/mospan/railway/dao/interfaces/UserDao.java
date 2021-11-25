package com.mospan.railway.dao.interfaces;

import com.mospan.railway.model.User;


public interface UserDao {
    void insert(User user);
    void delete(User user);
    void update(User user);
    User find(String login);
    User findByEmail(String email);
    User findById(long id);
    String getEmailSenderData();
}
