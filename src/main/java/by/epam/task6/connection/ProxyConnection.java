package by.epam.task6.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProxyConnection {
    private static Logger logger = LogManager.getLogger();
    private Connection connection;
    ProxyConnection(){}

    public void close() throws SQLException {
        logger.info("closing proxy connection");
        ProxyConnectionPool.getConnectionPool().releaseConnection(this);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

}
