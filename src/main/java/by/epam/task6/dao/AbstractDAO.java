package by.epam.task6.dao;

import by.epam.task6.entity.Entity;
import by.epam.task6.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;


public interface AbstractDAO<T extends Entity> {

    public abstract List<T> findAll() throws DaoException;

    public abstract T findEntityById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract boolean delete(T entity) throws DaoException;

    public abstract boolean create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    public void close() throws DaoException;
}

