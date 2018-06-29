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

public class CheckUserHandler implements RequestHandler {
    private LoginPageHandler loginPageHandler = new LoginPageHandler();
    private static Logger logger = LogManager.getLogger();
    private SHAConverter shaConverter = new SHAConverter();

    private ArrayList<User> getUserslist() throws DaoException {
        ArrayList<User> users = new ArrayList<>();
        try (UserDao userDao = new UserDao()){
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
        try {
            ArrayList<User> list = new ArrayList();
            list = getUserslist();
            String shalogin = shaConverter.convertToSHA1(login);
            String shaPassword = shaConverter.convertToSHA1(password);
            for (User user : list) {
                if (request.getSession().getAttribute("Login") == null) {
                    String loginDB = user.getLogin();
                    String passDB = user.getPassword();
                    if (shalogin.equals(loginDB) && shaPassword.equals(passDB)) {
                        request.getSession().setAttribute("Login", "ok");
                    }
                }
            }
        } catch (EncriptingException | DaoException e) {
            logger.error(e);
            throw new ServletException(e);
        }

        if (request.getSession().getAttribute("Login") == null) {
           loginPageHandler.execute(request, response);
        } else {
            if (request.getRequestDispatcher("/jsp/WelcomePage.jsp") != null) {
                request.getRequestDispatcher("/jsp/WelcomePage.jsp").forward(request, response);
            }
        }
        return "sucsess";
    }

    public void setShaConverter(SHAConverter shaConverter) {
        this.shaConverter = shaConverter;
    }
}

