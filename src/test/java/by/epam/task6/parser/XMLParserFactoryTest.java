package by.epam.task6.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XMLParserFactoryTest {
    private static Logger logger = LogManager.getLogger();
    private XMLParserFactory xmlParserFactory;
    private XMLParserBuilder xmlParserBuilder;

    @DataProvider
    public Object[][] parserOptions() {
        return new Object[][]{
                {"dom"},
                {"sax"},
                {"stax"}
        };
    }

    @BeforeMethod
    public void setUp() {
        xmlParserFactory = new XMLParserFactory();

    }

    @AfterMethod
    public void tearDown() {
        xmlParserBuilder = null;
        xmlParserFactory = null;
    }

    @Test(dataProvider = "parserOptions")
    public void testCreatePostcardBuilder(String parserType) {
        xmlParserBuilder = xmlParserFactory.createPostcardBuilder(parserType);
        xmlParserBuilder.buildPostcards("postcards/postcards.xml");
        logger.info(xmlParserBuilder.findPostcards());
    }
}