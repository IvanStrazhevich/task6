package by.epam.task6.service;

import by.epam.task6.dao.AuthorDao;
import by.epam.task6.dao.ProxyConnectionPool;
import by.epam.task6.entity.Author;
import by.epam.task6.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TableDataResolver {
    private static Logger logger = LogManager.getLogger();
    private AuthorDao authorDao = new AuthorDao(ProxyConnectionPool.getConnectionPool());


    public List<Author> findAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            authors = authorDao.findAll();
        } catch (DaoException e) {
            logger.info(e);
        } finally {
            try {
                authorDao.close();
            } catch (DaoException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return authors;
    }
}
