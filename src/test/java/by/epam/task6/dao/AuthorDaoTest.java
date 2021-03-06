package by.epam.task6.dao;

import by.epam.task6.connection.ProxyConnectionPool;
import by.epam.task6.dao.impl.AuthorDao;
import by.epam.task6.entity.Author;
import by.epam.task6.exception.DaoException;
import by.epam.task6.exception.ProxyPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class AuthorDaoTest {
    private AuthorDao authorDao;
    private Author author;
    private static Logger logger = LogManager.getLogger();
    private ProxyConnectionPool proxyConnectionPool;

    @BeforeClass
    public void beforeClass() {
        author = new Author();
        author.setAuthorName("John");
        author.setAuthorLastName("Smith");
        proxyConnectionPool = ProxyConnectionPool.getConnectionPool();

    }

    @AfterClass
    public void afterClass() throws ProxyPoolException {
        proxyConnectionPool.closeAll();
        proxyConnectionPool=null;

    }

    @BeforeMethod
    public void setUp() throws Exception {
        authorDao = new AuthorDao();

    }

    @AfterMethod
    public void tearDown() throws DaoException {
        authorDao.close();
    }

    @Test
    public void testFindAll() throws DaoException {
        System.out.println(authorDao.findAll());

    }

    @Test
    public void testFindEntityById() throws DaoException {
        authorDao.create(author);
        authorDao.findEntityById(authorDao.findLastInsertId());
    }

    @Test
    public void testDeleteByObject() throws DaoException {
        authorDao.create(author);
        authorDao.delete(author);
    }

    @Test
    public void testDeleteById() throws DaoException {
        authorDao.create(author);
        authorDao.delete(authorDao.findLastInsertId());
    }

    @Test
    public void testCreate() throws DaoException {
        authorDao.create(author);
    }

    @Test
    public void testUpdate() throws DaoException {
        authorDao.create(author);
        Author expected = authorDao.findEntityById(authorDao.findLastInsertId());
        expected.setAuthorName("Mike");
        authorDao.update(expected);
        logger.info("exp: " + expected);
        Author actual = authorDao.findEntityById(expected.getAuthorId());
        logger.info("act: " + actual);
        Assert.assertEquals(expected, actual);
    }
}
