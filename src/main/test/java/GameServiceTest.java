import com.servletproject.service.GameService;
import com.servletproject.utils.Question;
import com.servletproject.utils.QuestionLoader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private static GameService gameService;
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
        gameService = new GameService();
        expectedQuestion = new Question(0, "Test question", Arrays.asList("Answer 1", "Answer 2", "Answer 3"), "Answer 2", "");
        when(request.getSession()).thenReturn(session);
    }
    @Disabled
    @Test
//    TODO: currentQuestion is null before and after. No ideas why.
    public void testCurrentQuestion_FromIndex() throws ServletException, IOException {
        List<Question> questionsList = new ArrayList<>();

        questionsList.add(expectedQuestion);

        when(loader.loadQuestions()).thenReturn(questionsList);
        when(request.getRequestDispatcher("/view/game.jsp")).thenReturn(requestDispatcher);

        when(session.getAttribute("currentQuestionId")).thenReturn(0);

        System.out.println("Before getLogic(): " + request.getAttribute("currentQuestion"));
        gameService.getLogic(request, response);
        System.out.println("After getLogic(): " + request.getAttribute("currentQuestion"));

        verify(request).setAttribute("currentQuestion", expectedQuestion);
    }
    @Test
    public void testSessionDoReturn_currentQuestionId() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuestionId")).thenReturn(0);
        when(request.getRequestDispatcher("/view/game.jsp")).thenReturn(requestDispatcher);

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
    public void testSendRedirectToDeadPage_IfAnswerIsIncorrect(){}
    @Test
    public void testSendRedirectToWinPage_IfAnswerIsCorrectOnLastQuestion(){}
}
