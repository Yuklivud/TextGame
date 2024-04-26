package com.servletproject.service;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class StartService {
    public void getLogic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("currentQuestionId", 0);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/start.jsp");
        dispatcher.forward(req, resp);
    }
}
