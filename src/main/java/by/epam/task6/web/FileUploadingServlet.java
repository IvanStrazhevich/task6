package by.epam.task6.web;

import by.epam.task6.connection.ProxyConnectionPool;
import by.epam.task6.entity.Author;
import by.epam.task6.exception.DaoException;
import by.epam.task6.exception.ProxyPoolException;
import by.epam.task6.parser.ParserType;
import by.epam.task6.service.TableDataResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FileUploadingServlet",
        urlPatterns = {"/WelcomePage", "/UploadPage", "/UploadResultPage"})
@MultipartConfig(location = ""//The directory location where files will be stored
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
    Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "uploads";

    private void uploadFileShowUploadResultPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        if (null != request.getParts()) {
            for (Part part : request.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                    request.setAttribute("result", part.getSubmittedFileName() + " upload successfully. Parsed with ");
                }
            }
        }
        if (request.getRequestDispatcher("/jsp/UploadResultPage.jsp") != null) {
            String parser = request.getParameter("parser");
            logger.info("Parsed with: "+ parser);
            request.setAttribute("parser", parser);
            TableDataResolver dataResolver = new TableDataResolver();
            List<Author> authorList = dataResolver.findAllAuthors();
           /* ArrayList<Author> list = new ArrayList<>();*/
            request.setAttribute("authorsdao", authorList);
           /* request.setAttribute("authors", list);*/
            request.getRequestDispatcher("/jsp/UploadResultPage.jsp").forward(request, response);
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if (action.equals("WelcomePage")) {
            if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
            }
        } else if (action.equals("UploadPage")) {
            request.setAttribute("dom", ParserType.DOM);
            request.setAttribute("sax", ParserType.SAX);
            request.setAttribute("stax", ParserType.StAX);
            if (request.getRequestDispatcher("/jsp/UploadPage.jsp") != null) {
                request.getRequestDispatcher("/jsp/UploadPage.jsp").forward(request, response);
            }
        } else if (action.equals("UploadResultPage")) {
            uploadFileShowUploadResultPage(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleRequest(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            handleRequest(request, response);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}

