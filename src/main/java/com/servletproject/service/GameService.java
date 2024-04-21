package com.servletproject.service;

import com.servletproject.utils.Question;
import com.servletproject.utils.QuestionLoader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class GameService {
    private QuestionLoader loader = new QuestionLoader();
    private static final int TOTAL_NUM_OF_QUESTIONS = 10;

    public void getLogic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Question> questionsList = loader.loadQuestions();
        int currentQuestionIndex = (int) session.getAttribute("currentQuestionId");

        Question currentQuestion = questionsList.get(currentQuestionIndex);
        req.setAttribute("currentQuestion", currentQuestion);

        int currentProgressPercentage = (currentQuestionIndex * 100) / TOTAL_NUM_OF_QUESTIONS;
        req.setAttribute("currentProgressPercentage", currentProgressPercentage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/game.jsp");
        dispatcher.forward(req, resp);
    }
    public void postLogic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        List<Question> questionsList = loader.loadQuestions();
        int currentQuestionIndex = (int) session.getAttribute("currentQuestionId");
        String userAnswer = req.getParameter("userChoice");

        if(userAnswer.equals(questionsList.get(currentQuestionIndex).getCorrectAnswer()) && currentQuestionIndex != TOTAL_NUM_OF_QUESTIONS - 1){
            currentQuestionIndex++;
            session.setAttribute("currentQuestionId", currentQuestionIndex);
            resp.sendRedirect("/game");
        } else if (currentQuestionIndex == TOTAL_NUM_OF_QUESTIONS - 1) {
            session.setAttribute("currentQuestionId", 0);
            resp.sendRedirect("/view/win.jsp");
        } else {
            session.invalidate();
            resp.sendRedirect("/view/gameover.jsp");
        }
    }
}
