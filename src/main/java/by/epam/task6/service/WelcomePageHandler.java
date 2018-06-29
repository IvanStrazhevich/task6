package by.epam.task6.service;

import by.epam.task6.exception.WebXmlServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomePageHandler implements RequestHandler {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
            request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
        }
        return "success";
    }
}
