package com.example.psmsystem.model;

import java.util.List;

public interface IUserDao<T> {
    void addUser(T t);
    T checkLogin(String username, String password);
    boolean checkUsername(String username);
}
