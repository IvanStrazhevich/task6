package by.epam.task6.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StAXBuilderTest {
    private static Logger logger = LogManager.getLogger();
    private StAXBuilder staxBuilder;
    @BeforeMethod
    public void setUp() throws Exception {
        staxBuilder = new StAXBuilder();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        staxBuilder = null;
    }

    @Test
    public void testBuildPostcards() throws Exception {
        try {
            staxBuilder.buildPostcards("postcards/postcards.xml");
            logger.info("result " + staxBuilder.findPostcards());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}