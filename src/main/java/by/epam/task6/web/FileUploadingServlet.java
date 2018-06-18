package by.epam.task6.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "FileUploadingServlet",
        urlPatterns = {"/WelcomePage", "/UploadPage", "/ResultPage"})
@MultipartConfig(location = ""//The directory location where files will be stored
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if (action.equals("WelcomePage")) {
            if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
            }
        } else if (action.equals("UploadPage")) {
            if (request.getRequestDispatcher("/jsp/UploadPage.jsp") != null) {
                request.getRequestDispatcher("/jsp/UploadPage.jsp").forward(request, response);
            }
        } else if (action.equals("ResultPage")) {
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
                        if (request.getRequestDispatcher("/jsp/ResultPage.jsp") != null) {
                            request.setAttribute("result", part.getSubmittedFileName() + " upload successfully");
                            request.getRequestDispatcher("/jsp/ResultPage.jsp").forward(request, response);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }
}

