package by.epam.task6.service;

import by.epam.task6.entity.Postcard;
import by.epam.task6.exception.WebXmlServletException;
import by.epam.task6.parser.XMLParserBuilder;
import by.epam.task6.parser.XMLParserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlParseToTableHandler implements RequestHandler {
    Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "uploads";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = null;
        if (null != request.getParts()) {
            while (filename == null) {
                Part part = request.getParts().iterator().next();
                if (null != part.getSubmittedFileName()) {
                    filename = part.getSubmittedFileName();
                }
            }
        }
        String parser = request.getParameter("parser");
        XMLParserFactory xmlParserFactory = new XMLParserFactory();
        XMLParserBuilder xmlParserBuilder = xmlParserFactory.createPostcardBuilder(parser);
        xmlParserBuilder.buildPostcards(new File("").getAbsolutePath() + File.separator + UPLOAD_DIR + File.separator + filename);
        ArrayList<Postcard> postcards = xmlParserBuilder.findPostcards();
        request.setAttribute("parser", parser);
        request.setAttribute("result", filename);
        request.setAttribute("postcards", postcards);
        if (request.getRequestDispatcher("/jsp/UploadResultPage.jsp") != null) {
            request.getRequestDispatcher("/jsp/UploadResultPage.jsp").forward(request, response);
        }
        return "success";
    }
}
