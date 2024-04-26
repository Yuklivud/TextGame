package com.servletproject.service;

import com.servletproject.utils.Question;
import com.servletproject.utils.QuestionLoader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameService gameService;
    private static Question expectedQuestion;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private QuestionLoader loader;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.gameService = new GameService(loader);

        expectedQuestion = new Question(0, "Test question", Arrays.asList("Answer 1", "Answer 2", "Answer 3"), "Answer 2", "");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testCurrentQuestion_FromIndex() throws ServletException, IOException {
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(expectedQuestion);

        when(loader.loadQuestions()).thenReturn(questionsList);
        when(request.getRequestDispatcher("/view/game.jsp")).thenReturn(requestDispatcher);
        when(session.getAttribute("currentQuestionId")).thenReturn(0);

        gameService.getLogic(request, response);

        verify(request).setAttribute("currentQuestion", expectedQuestion);
    }
    @Test
    public void testSessionDoReturn_currentQuestionId() throws ServletException, IOException {
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(expectedQuestion);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuestionId")).thenReturn(0);
        when(request.getRequestDispatcher("/view/game.jsp")).thenReturn(requestDispatcher);
        when(loader.loadQuestions()).thenReturn(questionsList);

        gameService.getLogic(request, response);

        verify(session).getAttribute("currentQuestionId");
        assertNotNull(session.getAttribute("currentQuestionId"));
        assertEquals(0, (int) session.getAttribute("currentQuestionId"));
    }
    @Test
    public void testSendRedirectToNextQuestion_IfAnswerIsCorrect() throws IOException {
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(expectedQuestion);

        when(loader.loadQuestions()).thenReturn(questionsList);
        when(session.getAttribute("currentQuestionId")).thenReturn(0);
        when(request.getParameter("userChoice")).thenReturn("Answer 2");

        gameService.postLogic(request, response);

        verify(session).setAttribute(eq("currentQuestionId"), eq(1));
        verify(response).sendRedirect("/game");
        verify(session, never()).invalidate();
    }
    @Test
    public void testSendRedirectToDeadPage_IfAnswerIsIncorrect() throws IOException {
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(expectedQuestion);

        when(loader.loadQuestions()).thenReturn(questionsList);
        when(session.getAttribute("currentQuestionId")).thenReturn(0);
        when(request.getParameter("userChoice")).thenReturn("Answer 3");

        gameService.postLogic(request, response);

        verify(response).sendRedirect("/view/gameover.jsp");
        verify(session).invalidate();
    }
    @Test
    public void testSendRedirectToWinPage_IfAnswerIsCorrectOnLastQuestion() throws IOException {
        List<Question> questionsList = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            questionsList.add(expectedQuestion);
        }

        when(loader.loadQuestions()).thenReturn(questionsList);
        when(session.getAttribute("currentQuestionId")).thenReturn(9);
        when(request.getParameter("userChoice")).thenReturn("Answer 2");

        gameService.postLogic(request, response);

        verify(session).setAttribute(eq("currentQuestionId"), eq(0));
        verify(response).sendRedirect("/view/win.jsp");
    }
}