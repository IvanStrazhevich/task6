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
    private static final String MESSAGE_USER_EXIST = "User with this name already exist, try another";
    private static final String MESSAGE_USER_REGISTERED = "User registered";
    private static final String MESSAGE_USER_NOT_REGISTERED = "User is not registered, try again";
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
                    logger.info(loginDB);
                    logger.info(shalogin);
                    if (shalogin.equals(loginDB)) {
                        request.setAttribute("userExist", MESSAGE_USER_EXIST);
                        if (request.getRequestDispatcher("/jsp/RegisterPage.jsp") != null) {
                            logger.info("redirect to login page");
                            request.getRequestDispatcher("/jsp/RegisterPage.jsp").forward(request, response);
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
                    request.getSession().setAttribute("Login", "Logged");
                    request.setAttribute("userRegistered", MESSAGE_USER_REGISTERED);
                    if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                        request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("userNotRegistered", MESSAGE_USER_NOT_REGISTERED);
                    if (request.getRequestDispatcher("/jsp/RegisterPage.jsp") != null) {
                        request.getRequestDispatcher("/jsp/RegisterPage.jsp").forward(request, response);
                    }
                }
            }
        } catch (EncriptingException | DaoException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        return "sucsess";
    }
}
