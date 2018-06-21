package by.epam.task6.dao;


import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProxyConnectionPool {
    private static final int TIME_TILL_NEXT_TRY = 1000;
    private static Logger logger = LogManager.getLogger();
    private static ProxyConnectionPool connectionPool;
    private LinkedBlockingDeque<ProxyConnection> connectionPoolFree = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<ProxyConnection> connectionInUse = new LinkedBlockingDeque<>();
    private Properties properties = new Properties();
    private static ReentrantLock lock = new ReentrantLock();
    private Condition canConnect = lock.newCondition();
    private Condition canRelease = lock.newCondition();


    private ProxyConnectionPool() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("connection.properties"));
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
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

    public static final ProxyConnectionPool getConnectionPool() {
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

    void closeAll() throws ProxyPoolException {
        try {
            lock.lock();
            for (ProxyConnection proxyInUse : connectionInUse
                    ) {
                if (proxyInUse.getConnection() != null) {
                    proxyInUse.getConnection().close();
                    logger.info("Connection in using is closed" + proxyInUse.getConnection().isClosed());
                }
            }
            connectionInUse = null;
            for (ProxyConnection proxyFree : connectionPoolFree
                    ) {
                if (proxyFree.getConnection() != null) {
                    proxyFree.getConnection().close();
                    logger.info("Connection available is closed" + proxyFree.getConnection().isClosed());
                }
            }
            connectionInUse = null;

        } catch (SQLException e) {
            logger.error("Closing proxyConnection error", e);
            throw new ProxyPoolException("Closing proxyConnection error", e);

        } finally {
            try {
                DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
            } catch (SQLException e) {
                throw new ProxyPoolException(e);
            }
            lock.unlock();
        }
    }

    public ProxyConnection getConnection() {
        logger.info("Connections avalable" + connectionPoolFree.size());
        ProxyConnection proxyConnection = null;
        try {
            lock.lock();
            while (connectionPoolFree.isEmpty()) {
                canConnect.await(TIME_TILL_NEXT_TRY, TimeUnit.MILLISECONDS);
            }
            canConnect.signal();
            proxyConnection = connectionPoolFree.poll();
            logger.info("added" + proxyConnection);
            logger.info("pool free: " + connectionPoolFree);
            connectionInUse.add(proxyConnection);
            logger.info("pool in use: " + connectionInUse);
        } catch (InterruptedException e) {
            logger.error("Connection can't be established", e);
        } finally {
            lock.unlock();
        }
        return proxyConnection;
    }

    void releaseConnection(ProxyConnection proxyConnection) {
        try {
            lock.lock();
            while (connectionInUse.isEmpty()) {
                canRelease.await(TIME_TILL_NEXT_TRY, TimeUnit.MILLISECONDS);
            }
            canRelease.signal();
            connectionInUse.remove(proxyConnection);
            logger.info("Connection " + proxyConnection + " is in " + connectionInUse.contains(proxyConnection) + connectionInUse);
            logger.info("Connection returned to poll" + connectionInUse.isEmpty());
            connectionPoolFree.add(proxyConnection);
        } catch (InterruptedException e) {
            logger.error("Connection can't be released", e);
        } finally {
            lock.unlock();
        }
    }

    void setConnectionPoolFree(LinkedBlockingDeque<ProxyConnection> connectionPoolFree) {
        this.connectionPoolFree = connectionPoolFree;
    }
}
