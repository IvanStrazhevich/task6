package by.epam.task6.service;

import by.epam.task6.web.AttributeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class XmlReadHandler implements RequestHandler {
    private static final String UPLOAD_DIR = "uploads";
    private XmlParseToTableHandler xmlParseToTableHandler = new XmlParseToTableHandler();
    Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XmlParseToTableHandler.langDefinition(request);
        String applicationPath = request.getServletContext().getRealPath("");//new File("").getAbsolutePath();
        String uploadFilePath = applicationPath + UPLOAD_DIR;
        String filename = null;
        logger.info("1"+uploadFilePath + File.separator + filename);
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        if (null != request.getParts()) {
            for (Part part : request.getParts()) {
                if (null != part.getSubmittedFileName()) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                    filename = part.getSubmittedFileName();
                    logger.info("2"+ uploadFilePath + File.separator + filename);
                }
            }
        }
        logger.info("3"+ uploadFilePath + File.separator + filename);
        String page = xmlParseToTableHandler.execute(request, response);
        return page;
    }

    public void setXmlParseToTableHandler(XmlParseToTableHandler xmlParseToTableHandler) {
        this.xmlParseToTableHandler = xmlParseToTableHandler;
    }
}
