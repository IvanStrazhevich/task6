package by.epam.task6.service;

import by.epam.task6.dao.impl.AuthorDao;
import by.epam.task6.entity.Author;
import by.epam.task6.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TableDataResolver {
    private static Logger logger = LogManager.getLogger();

    public List<Author> findAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try (AuthorDao authorDao = new AuthorDao()) {
            authors = authorDao.findAll();
        } catch (DaoException e) {
            logger.info(e);
        }
        return authors;
    }
}

