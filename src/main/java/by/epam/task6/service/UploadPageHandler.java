package by.epam.task6.service;

import by.epam.task6.exception.WebXmlServletException;
import by.epam.task6.parser.ParserType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UploadPageHandler implements RequestHandler {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("dom", ParserType.DOM);
        request.setAttribute("sax", ParserType.SAX);
        request.setAttribute("stax", ParserType.StAX);
        if (request.getRequestDispatcher("/jsp/UploadPage.jsp") != null) {
            request.getRequestDispatcher("/jsp/UploadPage.jsp").forward(request, response);
        }
        return "success";
    }
}
