package by.epam.task6.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DomBuilderTest {
    private static Logger logger = LogManager.getLogger();
    DomBuilder domBuilder;

    @BeforeMethod
    public void setUp() throws Exception {
        domBuilder = new DomBuilder();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        domBuilder = null;
    }

    @Test
    public void testBuildSetPostcards() throws Exception {
        try {
            domBuilder.buildPostcards("postcards/postcards.xml");
            logger.info("Result Dom: "+ domBuilder.findPostcards());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}