package by.epam.task6.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPageHandler implements RequestHandler {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestDispatcher("/jsp/RegisterPage.jsp") != null) {
            request.getRequestDispatcher("/jsp/RegisterPage.jsp").forward(request, response);
        }
        return "success";
    }
}
