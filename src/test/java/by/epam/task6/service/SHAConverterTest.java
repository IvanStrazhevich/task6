package by.epam.task6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SHAConverterTest {
    private static Logger logger = LogManager.getLogger();
    SHAConverter shaConverter;

    @BeforeMethod
    public void setUp() throws Exception {
        shaConverter = new SHAConverter();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        shaConverter = null;
    }

    @Test
    public void testConvertToSHA1() throws Exception {
        String login = shaConverter.convertToSHA1("vanechkas@gmail.com");
        String password = shaConverter.convertToSHA1("DragonFly");
        logger.info("login is: "+login+'\n'+ "pass is: "+password);
    }
}