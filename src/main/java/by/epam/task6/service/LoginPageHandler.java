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

public class LoginPageHandler implements RequestHandler {
    private static final String MESSAGE = "message.needLogin";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XmlParseToTableHandler.langDefinition(request);
        String page = null;
        ResourceManager.INSTANCE.changeResource(new Locale(Config.FMT_LOCALE));
        if (request.getSession().getAttribute(AttributeEnum.LOGGED.getValue()) == null) {
            request.setAttribute(AttributeEnum.NEED_LOGIN.getValue(), ResourceManager.INSTANCE.getString(MESSAGE));
            page = PagesEnum.LOGIN_PAGE.getValue();
        } else {
            page = PagesEnum.WELCOME_PAGE.getValue();
        }
        return page;
    }
}
