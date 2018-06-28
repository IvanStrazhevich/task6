package by.epam.task6.dao;

import by.epam.task6.exception.DaoException;

import java.util.List;


public interface AbstractDao<T> extends AutoCloseable {
    int findLastInsertId() throws DaoException;

    List<T> findAll() throws DaoException;

    T findEntityById(int id) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean delete(T entity) throws DaoException;

    boolean create(T entity) throws DaoException;

    boolean update(T entity) throws DaoException;

    void close() throws DaoException;
}

