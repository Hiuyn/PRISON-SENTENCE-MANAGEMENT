package com.example.psmsystem.model;

public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String user_name;
    private String password;

    public User(String first_name, String last_name, String email, String user_name, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
    }

    @Override
    public String toString() {
        return first_name + " - " + last_name;
    }
}
