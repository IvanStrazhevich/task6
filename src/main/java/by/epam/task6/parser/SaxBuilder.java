package by.epam.task6.parser;

import by.epam.task6.entity.Author;
import by.epam.task6.entity.Postcard;
import by.epam.task6.entity.PostcardCharacteristics;
import by.epam.task6.entity.ValuablePostcardCharacteristics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaxBuilder extends XMLParserBuilder {
    private static Logger logger = LogManager.getLogger();
    private SAXParser saxParser;
    private PostcardHandler postcardHandler;

    public SaxBuilder() {
        postcardHandler = new PostcardHandler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            logger.error("Parser initialisation error", e);
        }
    }

    @Override
    public void buildPostcards(String fileLocation) {
        logger.info("Parsing with SAX");
        try {
            File inputFile = new File(fileLocation);
            saxParser.parse(inputFile, postcardHandler);

        } catch (SAXException | IOException e) {
            logger.error("SAXParsing error", e);
        }
    }

    @Override
    public ArrayList<Author> findAuthors() {
        return postcardHandler.getAuthors();
    }

    @Override
    public ArrayList<Postcard> findPostcards() {
        return postcardHandler.getPostcards();
    }

    @Override
    public ArrayList<PostcardCharacteristics> findCharactList() {
        return postcardHandler.getCharactList();
    }

    @Override
    public ArrayList<ValuablePostcardCharacteristics> findValCharactList() {
        return postcardHandler.getValCaractList();
    }

    public PostcardHandler getPostcardHandler() {
        return postcardHandler;
    }

    public void setPostcardHandler(PostcardHandler postcardHandler) {
        this.postcardHandler = postcardHandler;
    }
}

