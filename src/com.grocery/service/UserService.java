package com.grocery.service;

import com.grocery.dao.UserDAO;
import com.grocery.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        return userDAO.registerUser(user);
    }

    public User login(String email, String password) {
        return userDAO.loginUser(email, password);
    }
}
