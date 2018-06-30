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

public class RegisterUserHandler implements RequestHandler {
    private static final String MESSAGE_USER_EXIST ="message.userExist";
    private static final String MESSAGE_USER_REGISTERED = "message.userRegistered";
    private static final String MESSAGE_USER_NOT_REGISTERED = "message.userNotRegistered";
    private static Logger logger = LogManager.getLogger();
    private SHAConverter shaConverter = new SHAConverter();

    private boolean createUser(User user) throws DaoException {
        boolean flag = false;
        try (UserDao userDao = new UserDao()) {
            flag = userDao.create(user);

        } catch (DaoException e) {
            throw new DaoException(e);
        }
        return flag;
    }

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
        ResourceManager.INSTANCE.changeResource(new Locale(Config.FMT_LOCALE));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String shalogin = null;
        String shaPassword = null;
        try {
            ArrayList<User> list = new ArrayList();
            list = getUserslist();
            shalogin = shaConverter.convertToSHA1(login);
            shaPassword = shaConverter.convertToSHA1(password);
            boolean flag = false;
            for (User user : list) {
                if (request.getSession().getAttribute("Login") == null) {
                    String loginDB = user.getLogin();
                    if (shalogin.equals(loginDB)) {
                        request.setAttribute(AttributeEnum.USER_EXIST.getValue(), ResourceManager.INSTANCE.getString(MESSAGE_USER_EXIST));
                        if (request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()) != null) {
                            request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()).forward(request, response);
                            flag = true;
                            break;
                        }

                    }
                }
            }
            if (!flag) {
                User user = new User();
                user.setLogin(shalogin);
                user.setPassword(shaPassword);
                if (createUser(user)) {
                    request.getSession().setAttribute(AttributeEnum.LOGGED.getValue(), AttributeEnum.LANG.getValue());
                    request.setAttribute(AttributeEnum.USER_REGISTERED.getValue(), ResourceManager.INSTANCE.getString(MESSAGE_USER_REGISTERED));
                    if (request.getRequestDispatcher(PagesEnum.WELCOME_PAGE.getValue()) != null) {
                        request.getRequestDispatcher(PagesEnum.WELCOME_PAGE.getValue()).forward(request, response);
                    }
                } else {
                    request.setAttribute(AttributeEnum.USER_NOT_REGISTERED.getValue(), ResourceManager.INSTANCE.getString(MESSAGE_USER_NOT_REGISTERED));
                    if (request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()) != null) {
                        request.getRequestDispatcher(PagesEnum.REGISTER_PAGE.getValue()).forward(request, response);
                    }
                }
            }
        } catch (EncriptingException | DaoException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        return AttributeEnum.SUCCESS.getValue();
    }
}
