package by.epam.task6.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SaxBuilderTest {
    private static Logger logger = LogManager.getLogger();
private SaxBuilder saxBuilder;
    @BeforeMethod
    public void setUp() throws Exception {
        saxBuilder = new SaxBuilder();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        saxBuilder=null;
    }

    @Test
    public void testBuildPostcards() throws Exception {
        try {
            saxBuilder.buildPostcards("postcards/postcards.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
