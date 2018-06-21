package by.epam.task6.dao;

import by.epam.task6.entity.Author;
import by.epam.task6.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao implements AbstractDAO<Author> {
    private static final String SELECT_LAST_INSERT_ID_PSTM = "select last_insert_id()";
    private static final String SELECT_ALL_PSTM = "select au_id, au_name, au_lastname from author";
    private static final String SELECT_BY_ID_PSTM = "select au_id, au_name, au_lastname from author where au_id = ?";
    private static final String INSERT_PSTM = "insert into author(au_name, au_lastname) values(?,?)";
    private static final String DELETE_PSTM = "delete from author where au_id = ?";
    private static final String UPDATE_PSTM = "update author set au_name = ?, au_lastname = ? where au_id = ?";
    private static Logger logger = LogManager.getLogger();
    private ProxyConnection proxyConnection;

    public AuthorDao(ProxyConnectionPool connectionPool) {
        proxyConnection = connectionPool.getConnection();
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
    public List<Author> findAll() throws DaoException {
        ArrayList<Author> authorList = new ArrayList<>();
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SELECT_ALL_PSTM)) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Author author = new Author();
                author.setAuthorId(resultSet.getInt(1));
                author.setAuthorName(resultSet.getString(2));
                author.setAuthorLastName(resultSet.getString(3));
                authorList.add(author);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception on find all", e);
        }
        return authorList;
    }


    @Override
    public Author findEntityById(int id) throws DaoException {
        Author author = new Author();
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SELECT_BY_ID_PSTM)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            author.setAuthorId(resultSet.getInt(1));
            author.setAuthorName(resultSet.getString(2));
            author.setAuthorLastName(resultSet.getString(3));
        } catch (SQLException e) {
            throw new DaoException("Exception on find by id", e);
        }
        return author;
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
    public boolean delete(Author entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(DELETE_PSTM)) {
            preparedStatement.setInt(1, entity.getAuthorId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("Exception on delete", e);
        }
        return true;
    }

    @Override
    public boolean create(Author entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(INSERT_PSTM)) {
            preparedStatement.setString(1, entity.getAuthorName());
            preparedStatement.setString(2, entity.getAuthorLastName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Exception on create", e);
        }
        return true;
    }

    @Override
    public boolean update(Author entity) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(UPDATE_PSTM)) {
            preparedStatement.setString(1, entity.getAuthorName());
            preparedStatement.setString(2, entity.getAuthorLastName());
            preparedStatement.setInt(3, entity.getAuthorId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Exception on update", e);
        }
        return true;
    }


    public void close() throws DaoException {
        if (proxyConnection != null) {
            try {
                proxyConnection.close();
            } catch (SQLException e) {
                throw new DaoException("Exception on closing proxyConnection", e);
            }
        }
    }
}
