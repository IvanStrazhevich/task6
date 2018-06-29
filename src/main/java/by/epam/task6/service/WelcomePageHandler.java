package by.epam.task6.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomePageHandler implements RequestHandler {
    private static final String MESSAGE = "You have to log in or register";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("Login") != null) {
            if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("needLogin", MESSAGE);
            if (request.getRequestDispatcher("/jsp/LoginPage.jsp") != null) {
                request.getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
            }
        }
        return "success";
    }
}
