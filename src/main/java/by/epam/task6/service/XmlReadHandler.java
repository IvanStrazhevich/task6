package by.epam.task6.service;

import by.epam.task6.exception.WebXmlServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class XmlReadHandler implements RequestHandler {
    Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "uploads";
    private XmlParseToTableHandler xmlParseToTableHandler = new XmlParseToTableHandler();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationPath = new File("").getAbsolutePath();
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        if (null != request.getParts()) {
            for (Part part : request.getParts()) {
                if (null != part.getSubmittedFileName()) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                }
            }
        }
        xmlParseToTableHandler.execute(request, response);
        return "success";
    }

    public void setXmlParseToTableHandler(XmlParseToTableHandler xmlParseToTableHandler) {
        this.xmlParseToTableHandler = xmlParseToTableHandler;
    }
}
