package by.epam.task6.dao;

import by.epam.task6.entity.Entity;
import by.epam.task6.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;


public interface AbstractDAO<T extends Entity> extends AutoCloseable {
    int findLastInsertId() throws DaoException;

    List<T> findAll() throws DaoException;

    T findEntityById(int id) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean delete(T entity) throws DaoException;

    boolean create(T entity) throws DaoException;

    boolean update(T entity) throws DaoException;

    void close() throws DaoException;
}

