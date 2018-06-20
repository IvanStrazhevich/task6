package by.epam.task6.dao;


import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
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
    private volatile static ProxyConnectionPool connectionPool;
    private LinkedBlockingDeque<ProxyConnection> connectionPoolFree = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<ProxyConnection> connectionInUse = new LinkedBlockingDeque<>();
    private Properties properties = new Properties();
    private static ReentrantLock lock = new ReentrantLock();
    private Condition canConnect = lock.newCondition();
    private Condition canRelease = lock.newCondition();


    private ProxyConnectionPool() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("connection.properties"));
            String url = properties.getProperty("url");
            int poolsize = Integer.valueOf(properties.getProperty("poolsize"));
            for (int i = 0; i < poolsize; i++) {
                ProxyConnection connection = new ProxyConnection();
                connection.setConnection(DriverManager.getConnection(url, properties));
                connectionPoolFree.add(connection);
            }
        } catch (IOException e) {
            logger.error("Property file not found ", e);
        } catch (SQLException e) {
            logger.error("Data base is not reachable", e);
        }
    }

    protected static final ProxyConnectionPool getConnectionPool() {
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

    protected void closeAll() throws ProxyPoolException {
        try {
            lock.lock();
            for (ProxyConnection proxyInUse : connectionInUse
                    ) {
                if (proxyInUse.getConnection() != null) {
                    proxyInUse.getConnection().close();
                    logger.info(proxyInUse.getConnection().isClosed());
                }
            }
            connectionInUse = null;
            for (ProxyConnection proxyFree : connectionPoolFree
                    ) {
                if (proxyFree.getConnection() != null) {
                    proxyFree.getConnection().close();
                    logger.info(proxyFree.getConnection().isClosed());
                }
            }
            connectionInUse = null;

        } catch (SQLException e) {
            logger.error("Closing proxyConnection error", e);
            throw new ProxyPoolException("Closing proxyConnection error", e);

        } finally {
            /*logger.info("Connections left in use " + connectionInUse.size() + '\n'
                    + "Connections left free " + connectionPoolFree.size());*/
            lock.unlock();
        }
    }

    protected ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            lock.lock();
            while (connectionPoolFree.isEmpty()) {
                canConnect.await(TIME_TILL_NEXT_TRY, TimeUnit.MILLISECONDS);
            }
            canConnect.signal();
            connection = connectionPoolFree.poll();
            logger.info("added" + connection);
            logger.info("pool free" + connectionPoolFree);
            connectionInUse.add(connection);
            logger.info("pool in use"+connectionInUse);
        } catch (InterruptedException e) {
            logger.error("Connection can't be established", e);
        } finally {
            lock.unlock();
        }
        return connection;
    }

    protected void releaseConnection(ProxyConnection connection) {
        try {
            lock.lock();
            while (connectionInUse.isEmpty()) {
                canRelease.await(TIME_TILL_NEXT_TRY, TimeUnit.MILLISECONDS);
            }
            canRelease.signal();
            connectionInUse.remove(connection);
            logger.info("Connection " + connection+ " is in " + connectionInUse.contains(connection)+connectionInUse);
            logger.info(connectionInUse.isEmpty());
            connectionPoolFree.add(connection);
        } catch (InterruptedException e) {
            logger.error("Connection can't be released", e);
        } finally {
            lock.unlock();
        }
    }

    protected void setConnectionPoolFree(LinkedBlockingDeque<ProxyConnection> connectionPoolFree) {
        this.connectionPoolFree = connectionPoolFree;
    }
}
