package by.epam.task6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProxyConnection {
    private Connection connection;
    ProxyConnection(){}

    public void close() throws SQLException {
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
