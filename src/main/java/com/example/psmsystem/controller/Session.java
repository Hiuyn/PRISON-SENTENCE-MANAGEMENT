package com.example.psmsystem.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Session extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Lấy session hiện tại hoặc tạo mới nếu chưa có
        HttpSession session = request.getSession();

        // Đặt thuộc tính cho session
        session.setAttribute("username", "john_doe");
    }

}