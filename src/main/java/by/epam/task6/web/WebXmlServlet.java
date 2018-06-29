package by.epam.task6.web;

import by.epam.task6.exception.WebXmlServletException;
import by.epam.task6.service.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "WebXmlServlet",
        urlPatterns = {"/WelcomePage", "/UploadPage", "/UploadResultPage", "/LoginPage", "/CheckLogin", "/RegisterUser"})
@MultipartConfig(location = ""//The directory location where files will be stored
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class WebXmlServlet extends HttpServlet {
    Logger logger = LogManager.getLogger();
    HashMap<String, RequestHandler> servletMap;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            servletMap = MapCreator.getInstance().getServletMap();
        } catch (WebXmlServletException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    /* private static final String UPLOAD_DIR = "uploads";

    private void uploadFileShowUploadResultPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        String filename = null;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        if (null != request.getParts()) {
            for (Part part : request.getParts()) {
                if (null != part.getSubmittedFileName()) {
                    filename = part.getSubmittedFileName();
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                }
            }
        }
        String parser = request.getParameter("parser");
        XMLParserFactory xmlParserFactory = new XMLParserFactory();
        XMLParserBuilder xmlParserBuilder = xmlParserFactory.createPostcardBuilder(parser);
        xmlParserBuilder.buildPostcards(applicationPath + File.separator + UPLOAD_DIR + File.separator + filename);
        ArrayList<Postcard> postcards = xmlParserBuilder.findPostcards();
        logger.info(postcards);
        logger.info("Parsed with: " + parser);
        request.setAttribute("parser", parser);
        request.setAttribute("result", filename + " uploaded successfully. Parsed with ");
        request.setAttribute("postcards", postcards);
        if (request.getRequestDispatcher("/jsp/UploadResultPage.jsp") != null) {
            request.getRequestDispatcher("/jsp/UploadResultPage.jsp").forward(request, response);
        }
    }*/

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
       /* if (request.getSession().getAttribute("Login")==null){
            action = CommandEnum.valueOf(CommandEnum.LOGIN_PAGE.toString()).value;
        }*/
        RequestHandler requestHandler = servletMap.get(action);
        requestHandler.execute(request, response);
        /*  if (action.equals("WelcomePage")) {
          *//*  if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
            }*//*
        } else if (action.equals("UploadPage")) {
      *//*      request.setAttribute("dom", ParserType.DOM);
            request.setAttribute("sax", ParserType.SAX);
            request.setAttribute("stax", ParserType.StAX);
            if (request.getRequestDispatcher("/jsp/UploadPage.jsp") != null) {
                request.getRequestDispatcher("/jsp/UploadPage.jsp").forward(request, response);
            }*//*
        } else if (action.equals("UploadResultPage")) {
            *//* uploadFileShowUploadResultPage(request, response);*//*
        }*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request,response);
    }
}

