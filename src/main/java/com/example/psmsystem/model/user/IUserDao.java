package com.example.psmsystem.model.user;

import java.util.List;

public interface IUserDao<T> {
    void addUser(T t);
    T checkLogin(String username, String password);
    boolean checkUsername(String username);
    List<T> getAllUsers();
}
