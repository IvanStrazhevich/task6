package by.epam.task6.service;

import by.epam.task6.ResourceManager;
import by.epam.task6.parser.ParserType;
import by.epam.task6.web.AttributeEnum;
import by.epam.task6.web.PagesEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

public class UploadPageHandler implements RequestHandler {
    private static final String MESSAGE = "message.needLogin";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (null != request.getParameter(AttributeEnum.LANG.getValue())) {
            switch (request.getParameter(AttributeEnum.LANG.getValue())) {
                case "be":
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("be", "BY"));
                    request.getSession().setAttribute(AttributeEnum.LANG.getValue(), "be");
                    break;
                case "ru":
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("ru", "RU"));
                    request.getSession().setAttribute(AttributeEnum.LANG.getValue(), "ru");
                    break;
                default:
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("en", "US"));
                    request.getSession().setAttribute(AttributeEnum.LANG.getValue(), "en");
            }
        } else {
            XmlParseToTableHandler.langDefinition(request);
        }
        String page = null;
        ResourceManager.INSTANCE.changeResource(new Locale(Config.FMT_LOCALE));
        if (request.getSession().getAttribute(AttributeEnum.LOGGED.getValue()) != null) {
            request.setAttribute(AttributeEnum.DOM.getValue(), ParserType.DOM);
            request.setAttribute(AttributeEnum.SAX.getValue(), ParserType.SAX);
            request.setAttribute(AttributeEnum.STAX.getValue(), ParserType.StAX);
            page=PagesEnum.UPLOAD_PAGE.getValue();
        } else {
            request.setAttribute(AttributeEnum.NEED_LOGIN.getValue(), ResourceManager.INSTANCE.getString(MESSAGE));
            page=PagesEnum.LOGIN_PAGE.getValue();
        }
        return page;
    }
}
