package by.epam.task6.web;

import by.epam.task6.exception.WebXmlServletException;
import by.epam.task6.service.*;

import java.util.HashMap;

public class MapCreator {
    private static MapCreator instance;
    private HashMap<String,RequestHandler> servletMap = new HashMap<>();

    private MapCreator() throws WebXmlServletException {

        for (CommandEnum command: CommandEnum.values()
                ) {
            switch (command){
                case LOGIN_PAGE:
                    servletMap.put(command.value, new LoginPageHandler());
                    break;
                case CHECK_LOGIN:
                    servletMap.put(command.value, new CheckUserHandler());
                    break;
                case WELCOME_PAGE:
                    servletMap.put(command.value, new WelcomePageHandler());
                    break;
                case UPLOAD_PAGE:
                    servletMap.put(command.value, new UploadPageHandler());
                    break;
                case UPLOAD_RESULT_PAGE:
                    servletMap.put(command.value, new XmlReadHandler());
                    break;
                case REGISTER_USER:
                    servletMap.put(command.value, new RegisterUserHandler());
                    break;
                case REGISTER_PAGE:
                    servletMap.put(command.value, new RegisterPageHandler());
                    break;
                default:
                    throw new WebXmlServletException("There is no such command at "+ CommandEnum.class +command.value);
            }
        }
    }

    static MapCreator getInstance() throws WebXmlServletException {
        if (instance==null){
            instance = new MapCreator();
        }
        return instance;
    }

    HashMap<String, RequestHandler> getServletMap() {
        return servletMap;
    }
}
