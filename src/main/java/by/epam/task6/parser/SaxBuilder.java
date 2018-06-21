package by.epam.task6.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SaxBuilder {
    private static Logger logger = LogManager.getLogger();
    private SAXParser saxParser;
    private PostcardHandler postcardHandler;

    public SaxBuilder() {
        postcardHandler = new PostcardHandler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void buildPostcards(String fileLocation) {
        try {
            File inputFile = new File(fileLocation);
            saxParser.parse(inputFile, postcardHandler);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PostcardHandler getPostcardHandler() {
        return postcardHandler;
    }

    public void setPostcardHandler(PostcardHandler postcardHandler) {
        this.postcardHandler = postcardHandler;
    }
}

