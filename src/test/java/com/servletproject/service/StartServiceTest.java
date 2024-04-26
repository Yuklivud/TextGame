package com.servletproject.service;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StartServiceTest {
    private StartService service;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new StartService();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testStartService_doForwardWithQuestionId() throws ServletException, IOException {
        when(request.getRequestDispatcher("/view/start.jsp")).thenReturn(requestDispatcher);

        service.getLogic(request, response);

        verify(session).setAttribute("currentQuestionId", 0);
        verify(request.getRequestDispatcher("/view/start.jsp")).forward(request, response);
    }
}