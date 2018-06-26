package by.epam.task6.connection;


import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProxyConnectionPool {
    private static final int MAX_CONNECTIONS = 32;
    private static final int MIN_CONNECTIONS = 10;
    private static final int NORMALIZATION_LIMIT_FOR_CONNECTIONS = 20;
    private static Logger logger = LogManager.getLogger();
    private static ProxyConnectionPool connectionPool;
    private LinkedBlockingQueue<ProxyConnection> connectionPoolFree = new LinkedBlockingQueue<>();
    private ArrayList<ProxyConnection> connectionInUse = new ArrayList<>();
    private Properties properties = new Properties();
    private static ReentrantLock lock = new ReentrantLock();

    private ProxyConnectionPool() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("connection.properties"));
            String url = properties.getProperty("url");
            int poolsize = Integer.valueOf(properties.getProperty("poolsize"));
            for (int i = 0; i < poolsize; i++) {
                ProxyConnection proxyConnection = new ProxyConnection();
                proxyConnection.setConnection(DriverManager.getConnection(url, properties));
                connectionPoolFree.add(proxyConnection);
            }
        } catch (IOException e) {
            logger.error("Property file not found ", e);
        } catch (SQLException e) {
            logger.error("Data base is not reachable", e);
        }
    }

    public static ProxyConnectionPool getConnectionPool() {
        if (null == connectionPool) {
            try {
                lock.lock();
                if (null == connectionPool) {
                    connectionPool = new ProxyConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    public void closeAll() throws ProxyPoolException {
        try {
            int poolsize = connectionPoolFree.size();
            for (int i = 0; i < poolsize; i++) {
                logger.info(connectionPoolFree.size() + "i: " + i);
                connectionPoolFree.take().getConnection().close();
            }
        } catch (SQLException | InterruptedException e) {
            throw new ProxyPoolException("Closing proxyConnection error", e);
        }
    }
    private void optimizePool() throws ProxyPoolException {
        try {
            while (connectionPoolFree.size() > MIN_CONNECTIONS) {
                logger.info("Optimisation " + connectionPoolFree.size());
                connectionPoolFree.take().getConnection().close();
            }
        } catch (SQLException | InterruptedException e) {
            throw new ProxyPoolException("Closing proxyConnection error", e);
        }
    }

    public ProxyConnection getConnection() throws ProxyPoolException {
        logger.info("Connections avalable" + connectionPoolFree.size());
        ProxyConnection proxyConnection = null;
        try {
            if (connectionPoolFree.size() == 0 && connectionPoolFree.size() < MAX_CONNECTIONS) {
                logger.info("Connection adding");
                String url = properties.getProperty("url");
                ProxyConnection additionalProxyConnection = new ProxyConnection();
                additionalProxyConnection.setConnection(DriverManager.getConnection(url, properties));
                connectionPoolFree.add(additionalProxyConnection);
            } else if (connectionPoolFree.size() > NORMALIZATION_LIMIT_FOR_CONNECTIONS) {
                optimizePool();
            }
            proxyConnection = connectionPoolFree.take();
        } catch (InterruptedException e) {
            throw new ProxyPoolException("Gettinging proxyConnection error", e);
        } catch (SQLException e) {
            throw new ProxyPoolException("Adding proxyConnection error", e);
        }
        logger.info("added" + proxyConnection);
        logger.info("pool free: " + connectionPoolFree);
        connectionInUse.add(proxyConnection);
        logger.info("pool in use: " + connectionInUse);

        return proxyConnection;
    }

    void releaseConnection(ProxyConnection proxyConnection) {
        connectionInUse.remove(proxyConnection);
        logger.info("Connection " + proxyConnection + " is in " + connectionInUse.contains(proxyConnection) + connectionInUse);
        logger.info("Connection returned to poll " + connectionInUse.isEmpty());
        connectionPoolFree.add(proxyConnection);
    }
}
