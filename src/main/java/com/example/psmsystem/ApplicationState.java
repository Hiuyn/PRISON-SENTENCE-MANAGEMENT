package com.example.psmsystem;

public class ApplicationState {
    private static ApplicationState instance;
    private String username;
    private int id;

    // Private constructor to prevent instantiation
    private ApplicationState() {}

    // Static method to get the single instance of the class
    public static ApplicationState getInstance() {
        if (instance == null) {
            instance = new ApplicationState();
        }
        return instance;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

