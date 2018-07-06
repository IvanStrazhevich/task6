package by.epam.task6.service;


import by.epam.task6.ResourceManager;
import by.epam.task6.dao.impl.UserDao;
import by.epam.task6.entity.User;
import by.epam.task6.exception.DaoException;
import by.epam.task6.exception.EncriptingException;
import by.epam.task6.web.AttributeEnum;
import by.epam.task6.web.PagesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class CheckUserHandler implements RequestHandler {
    private static Logger logger = LogManager.getLogger();
    private SHAConverter shaConverter = new SHAConverter();
    private static final String MESSAGE = "message.wrongloginAndPass";
    private static final String MESSAGE_SUCCESS = "message.loginOk";

    private ArrayList<User> getUserslist() throws DaoException {
        ArrayList<User> users = new ArrayList<>();
        try (UserDao userDao = new UserDao()) {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XmlParseToTableHandler.langDefinition(request);
        String page = null;
        ResourceManager.INSTANCE.changeResource(new Locale(Config.FMT_LOCALE));
        String login = request.getParameter(AttributeEnum.LOGIN.getValue());
        String password = request.getParameter(AttributeEnum.PASSWORD.getValue());
        Boolean logeed = false;
        try {
            ArrayList<User> list = new ArrayList();
            list = getUserslist();
            String shaLogin = shaConverter.convertToSHA1(login);
            String shaPassword = shaConverter.convertToSHA1(password);
            for (int i = 0; i < list.size() && !logeed; i++) {
                User user = list.get(i);
                if (request.getSession().getAttribute(AttributeEnum.LOGGED.getValue()) == null) {
                    String loginDB = user.getLogin();
                    String passDB = user.getPassword();
                    if (shaLogin.equals(loginDB) && shaPassword.equals(passDB)) {
                        request.getSession().setAttribute(AttributeEnum.LOGGED.getValue(), AttributeEnum.LOGGED.getValue());
                        logeed = true;
                        request.setAttribute(AttributeEnum.GREETING.getValue(), ResourceManager.INSTANCE.getString(MESSAGE_SUCCESS));
                        page = PagesEnum.WELCOME_PAGE.getValue();
                    } else {
                        request.getSession().setAttribute(AttributeEnum.NEED_REGISTER.getValue(), ResourceManager.INSTANCE.getString(MESSAGE));
                        page = PagesEnum.LOGIN_PAGE.getValue();
                    }
                }
            }
        } catch (EncriptingException | DaoException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        return page;
    }

    public void setShaConverter(SHAConverter shaConverter) {
        this.shaConverter = shaConverter;
    }
}

