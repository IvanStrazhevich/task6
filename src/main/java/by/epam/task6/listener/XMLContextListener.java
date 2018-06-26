package by.epam.task6.listener;

import by.epam.task6.connection.ProxyConnectionPool;
import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;
@WebListener
public class XMLContextListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger();
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
        } finally {
            DriverManager.drivers().forEach(s -> {
                logger.info("Closing Drivers");
                try {
                    DriverManager.deregisterDriver(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
