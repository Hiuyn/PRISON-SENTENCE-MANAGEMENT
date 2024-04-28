package com.example.psmsystem.model;

public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String user_name;
    private String password;

    public User(){}

    public User(String first_name, String last_name, String email, String user_name, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return first_name + " - " + last_name;
    }
}
