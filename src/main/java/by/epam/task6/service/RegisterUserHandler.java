package by.epam.task6.service;

import by.epam.task6.dao.impl.UserDao;
import by.epam.task6.entity.User;
import by.epam.task6.exception.DaoException;
import by.epam.task6.exception.EncriptingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterUserHandler implements RequestHandler {
    private RequestHandler requestHandler;
    private static final String MESSAGE_USER_EXIST = "User with this name already exist, try another";
    private static final String MESSAGE_USER_REGISTERED = "User registered";
    private static final String MESSAGE_USER_NOT_REGISTERED = "User is not registered, try again";
    private static Logger logger = LogManager.getLogger();
    private SHAConverter shaConverter = new SHAConverter();

    private boolean createUser(User user) throws DaoException {

        try (UserDao userDao = new UserDao()) {
            userDao.create(user);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
        return true;
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
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String shalogin = null;
        String shaPassword = null;
        try {
            ArrayList<User> list = new ArrayList();
            list = getUserslist();
            shalogin = shaConverter.convertToSHA1(login);
            shaPassword = shaConverter.convertToSHA1(password);
            for (User user : list) {
                if (request.getSession().getAttribute("Login") == null) {
                    String loginDB = user.getLogin();
                    String passDB = user.getPassword();
                    if (shalogin.equals(loginDB) && shaPassword.equals(passDB)) {
                        request.setAttribute("userExist", MESSAGE_USER_EXIST);
                        requestHandler = new LoginPageHandler();
                        requestHandler.execute(request, response);
                    }
                }
            }
            if (request.getSession().getAttribute("Login") == null) {
                User user = new User();
                user.setLogin(shalogin);
                user.setPassword(shaPassword);
                if (createUser(user)) {
                    request.setAttribute("userRegistered", MESSAGE_USER_REGISTERED);
                    requestHandler = new WelcomePageHandler();
                    requestHandler.execute(request, response);
                } else {
                    request.setAttribute("userNotRegistered", MESSAGE_USER_NOT_REGISTERED);
                    requestHandler = new LoginPageHandler();
                    requestHandler.execute(request, response);
                }
            }
        } catch (EncriptingException | DaoException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        return "sucsess";
    }
}
