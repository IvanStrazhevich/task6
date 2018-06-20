package by.epam.task6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProxyConnection {
    private Connection connection;

    public void close() throws SQLException {
        ProxyConnectionPool.getConnectionPool().releaseConnection(this);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    protected Connection getConnection() {
        return connection;
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }
}
