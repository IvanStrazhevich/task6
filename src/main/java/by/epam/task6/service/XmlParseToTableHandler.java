package by.epam.task6.service;

import by.epam.task6.entity.Postcard;
import by.epam.task6.exception.WebXmlServletException;
import by.epam.task6.parser.XMLParserBuilder;
import by.epam.task6.parser.XMLParserFactory;
import by.epam.task6.web.AttributeEnum;
import by.epam.task6.web.PagesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.jsp.jstl.core.Config;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlParseToTableHandler implements RequestHandler {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        langDefinition(request);
        String filename = null;
        if (null != request.getParts()) {
            while (filename == null) {
                Part part = request.getParts().iterator().next();
                if (null != part.getSubmittedFileName()) {
                    filename = part.getSubmittedFileName();
                }
            }
        }
        String parser = request.getParameter(AttributeEnum.PARSER.getValue());
        XMLParserFactory xmlParserFactory = new XMLParserFactory();
        XMLParserBuilder xmlParserBuilder = xmlParserFactory.createPostcardBuilder(parser);
        xmlParserBuilder.buildPostcards(new File("").getAbsolutePath() + File.separator + UPLOAD_DIR + File.separator + filename);
        ArrayList<Postcard> postcards = xmlParserBuilder.findPostcards();
        request.setAttribute(AttributeEnum.PARSER.getValue(), parser);
        request.setAttribute(AttributeEnum.RESULT.getValue(), filename);
        request.setAttribute(AttributeEnum.POSTCARDS.getValue(), postcards);
        if (request.getRequestDispatcher(PagesEnum.UPLOAD_RESULT_PAGE.getValue()) != null) {
            request.getRequestDispatcher(PagesEnum.UPLOAD_RESULT_PAGE.getValue()).forward(request, response);
        }
        return AttributeEnum.SUCCESS.getValue();
    }

    static void langDefinition(HttpServletRequest request) {
        if (null != request.getSession().getAttribute(AttributeEnum.LANG.getValue())) {
            switch (request.getSession().getAttribute(AttributeEnum.LANG.getValue()).toString()) {
                case "be":
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("be", "BY"));
                    break;
                case "ru":
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("ru", "RU"));
                    break;
                default:
                    Config.set(request, Config.FMT_LOCALE, new java.util.Locale("en", "US"));
            }
        }
    }
}
