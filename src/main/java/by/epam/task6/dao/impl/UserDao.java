package by.epam.task6.dao.impl;

import by.epam.task6.connection.ProxyConnection;
import by.epam.task6.connection.ProxyConnectionPool;
import by.epam.task6.dao.AbstractDao;
import by.epam.task6.entity.User;
import by.epam.task6.exception.DaoException;
import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements AbstractDao<User> {
    private static final String SELECT_LAST_INSERT_ID_PSTM = "select last_insert_id()";
    private static final String SELECT_ALL_PSTM = "select user_id, us_login, us_password, us_photo from user";
    private static final String SELECT_BY_ID_PSTM = "select user_id, us_login, us_password, us_photo from user where user_id = ?";
    private static final String INSERT_PSTM = "insert into user(us_login, us_password, us_photo) values(?,?,?)";
    private static final String DELETE_PSTM = "delete from user where user_id = ?";
    private static final String UPDATE_PSTM = "update user set us_login = ?, us_password = ?, us_photo = ? where user_id = ?";
    private static Logger logger = LogManager.getLogger();
    private ProxyConnection proxyConnection;

    public UserDao() throws DaoException {
        try {
            proxyConnection = ProxyConnectionPool.getConnectionPool().getConnection();
        } catch (ProxyPoolException e) {
            throw new DaoException("There is no free connection", e);
        }
    }

    @Override
    public int findLastInsertId() throws DaoException {
    int id = 0;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SELECT_LAST_INSERT_ID_PSTM)) {
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        id = resultSet.getInt(1);
    } catch (SQLException e) {
        throw new DaoException("Exception on find last id", e);
    }
        return id;
}
    @Override
    public ArrayList<User> findAll() throws DaoException {
        ArrayList<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SELECT_ALL_PSTM)) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhoto(resultSet.getBlob(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception on find all", e);
        }
        return userList;
    }


    @Override
    public User findEntityById(int id) throws DaoException {
        User user = new User();
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SELECT_BY_ID_PSTM)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            user.setUserId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setPhoto(resultSet.getBlob(4));
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("Exception on find by id", e);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(DELETE_PSTM)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Exception on delete", e);
        }
        return true;
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(DELETE_PSTM)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Exception on delete", e);
        }
        return true;
    }

    @Override
    public boolean create(User entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(INSERT_PSTM)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setBlob(3, entity.getPhoto());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DaoException("Exception on create", e);
        }
        return true;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(UPDATE_PSTM)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setBlob(3, entity.getPhoto());
            preparedStatement.setInt(4, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Exception on update", e);
        }
        return true;
    }


    public void close() throws DaoException {
        if (proxyConnection != null) {
            try {
                logger.info("closing dao");
                proxyConnection.close();

            } catch (SQLException e) {
                throw new DaoException("Exception on closing proxyConnection", e);
            }
        }
    }
}
