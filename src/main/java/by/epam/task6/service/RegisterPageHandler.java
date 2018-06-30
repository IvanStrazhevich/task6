package by.epam.task6.service;

import by.epam.task6.web.AttributeEnum;
import by.epam.task6.web.PagesEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPageHandler implements RequestHandler {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XmlParseToTableHandler.langDefinition(request);
        if (request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()) != null) {
            request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()).forward(request, response);
        }
        return AttributeEnum.SUCCESS.getValue();
    }
}
