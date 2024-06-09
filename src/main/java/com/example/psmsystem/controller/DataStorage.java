package com.example.psmsystem.controller;

import java.util.Map;

public class DataStorage {
    private static Map<Integer, Integer> crimesTime;

    // Phương thức setter để đặt dữ liệu


    public static Map<Integer, Integer> getCrimesTime() {
        return crimesTime;
    }

    public static void setCrimesTime(Map<Integer, Integer> crimesTime) {
        DataStorage.crimesTime = crimesTime;
    }
}
