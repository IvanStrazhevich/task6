package by.epam.task6.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestHandler {
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
