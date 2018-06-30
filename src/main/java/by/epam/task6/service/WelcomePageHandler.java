package by.epam.task6.service;

import by.epam.task6.ResourceManager;
import by.epam.task6.web.AttributeEnum;
import by.epam.task6.web.PagesEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

public class WelcomePageHandler implements RequestHandler {
    private static final String MESSAGE = "message.needLogin";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XmlParseToTableHandler.langDefinition(request);
        ResourceManager.INSTANCE.changeResource(new Locale(Config.FMT_LOCALE));
        if (request.getSession().getAttribute(AttributeEnum.LOGGED.getValue()) != null) {
            if (request.getRequestDispatcher(PagesEnum.WELCOME_PAGE.getValue()) != null) {
                request.getRequestDispatcher(PagesEnum.WELCOME_PAGE.getValue()).forward(request, response);
            }
        } else {
            request.setAttribute(AttributeEnum.NEED_LOGIN.getValue(), ResourceManager.INSTANCE.getString(MESSAGE));
            if (request.getRequestDispatcher(PagesEnum.LOGIN_PAGE.getValue()) != null) {
                request.getRequestDispatcher(PagesEnum.LOGIN_PAGE.getValue()).forward(request, response);
            }
        }
        return AttributeEnum.SUCCESS.getValue();
    }
}
