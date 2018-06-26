package by.epam.task6.listener;

import by.epam.task6.connection.ProxyConnectionPool;
import by.epam.task6.exception.ProxyPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;
@WebListener
public class XMLContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ProxyConnectionPool.getConnectionPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ProxyConnectionPool.getConnectionPool().closeAll();
        } catch (ProxyPoolException e) {
            e.printStackTrace();
        }
    }
}
