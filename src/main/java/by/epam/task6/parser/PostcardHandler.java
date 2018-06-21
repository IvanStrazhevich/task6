package by.epam.task6.parser;

import by.epam.task6.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Year;
import java.util.ArrayList;

public class PostcardHandler extends DefaultHandler {
    private static Logger logger = LogManager.getLogger();
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Postcard> postcards = new ArrayList<>();
    private ArrayList<PostcardCharacteristics> charactList = new ArrayList<>();
    private ArrayList<ValuablePostcardCharacteristics> valCaractList = new ArrayList<>();
    private ValuablePostcardCharacteristics valuablePostcardCharacteristics = new ValuablePostcardCharacteristics();
    private Author author = new Author();
    private Postcard postcard = new Postcard();
    private PostcardCharacteristics postcardCharacteristics = new PostcardCharacteristics();
    private CardType cardType;
    private Valuable valuable;


    boolean bTheme = false;
    boolean bPostcardsCharacteristics = false;
    boolean bCountry = false;
    boolean bValuablePostcardsCharacteristics = false;
    boolean bYear = false;
    boolean bAuthor = false;
    boolean bName = false;
    boolean bLastName = false;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        logger.info("start of " + qName);
        if (qName.equalsIgnoreCase("postcard")) {
            postcard = new Postcard();
            String cardTypeValue = attributes.getValue("card-type");
            logger.info(cardTypeValue);
            cardType = CardType.valueOf(cardTypeValue.toUpperCase());
            postcard.setCardType(cardType);
            postcard.setSent(Boolean.parseBoolean(attributes.getValue("sent")));
            postcard.setPostcardId(attributes.getValue("postcardId"));
        } else if (qName.equalsIgnoreCase("theme")) {
            bTheme = true;
        } else if (qName.equalsIgnoreCase("postcards-characteristics")) {

            postcardCharacteristics = new PostcardCharacteristics();
            bPostcardsCharacteristics = true;
            if (qName.equalsIgnoreCase("country")) {
                logger.info(postcard);
                bCountry = true;
            }
        } else if (qName.equalsIgnoreCase("valuable-postcards-characteristics")) {
            valuablePostcardCharacteristics = new ValuablePostcardCharacteristics();
            String valuableValue = attributes.getValue("valuable");
            valuable = Valuable.valueOf(valuableValue.toUpperCase());
            valuablePostcardCharacteristics.setValuable(valuable);
            bValuablePostcardsCharacteristics = true;
            if (qName.equalsIgnoreCase("country")) {
                bCountry = true;
            }
        } else if (qName.equalsIgnoreCase("year")) {
            bYear = true;
        } else if (qName.equalsIgnoreCase("author")) {
            bAuthor = true;
        } else if (qName.equalsIgnoreCase("name")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
        }

    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        logger.info("end of " + qName);
        if (qName.equalsIgnoreCase("postcard")) {
            postcards.add(postcard);
        } else if (qName.equalsIgnoreCase("valuable-postcards-characteristics")) {
            valuablePostcardCharacteristics.setPostcardsCharacteristicsId(postcard.getPostcardId());
            postcard.setPostcardCharachteristics(valuablePostcardCharacteristics);
            valCaractList.add(valuablePostcardCharacteristics);
        } else if (qName.equalsIgnoreCase("author")) {
            author.setAuthorId(Integer.valueOf(postcard.getPostcardId().replace("card", "")));
            valuablePostcardCharacteristics.setAuthorId(author.getAuthorId());
            logger.info(author);
            authors.add(author.getAuthorId() - 1, author);
            logger.info(authors);
        }
    }


    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bTheme) {
            postcard.setTheme(Theme.valueOf(new String(ch, start, length).toUpperCase().replace(" ", "_")));
            bTheme = false;
        } else if (bCountry) {
            valuablePostcardCharacteristics.setCountry(Country.valueOf(new String(ch, start, length)));
            bCountry = false;
        } else if (bYear) {
            valuablePostcardCharacteristics.setYear(Year.parse(new String(ch, start, length)));
            bYear = false;
        } else if (bName) {
            author.setAuthorName(new String(ch, start, length));
            bName = false;
        } else if (bLastName) {
            author.setAuthorLastName(new String(ch, start, length));
            bLastName = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        logger.info(postcards);
        logger.info(authors);
        logger.info(valCaractList);
        super.endDocument();
    }

    public ArrayList<Postcard> getPostcards() {
        return postcards;
    }

    public void setPostcards(ArrayList<Postcard> postcards) {
        this.postcards = postcards;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<PostcardCharacteristics> getCharactList() {
        return charactList;
    }

    public void setCharactList(ArrayList<PostcardCharacteristics> charactList) {
        this.charactList = charactList;
    }

    public ArrayList<ValuablePostcardCharacteristics> getValCaractList() {
        return valCaractList;
    }

    public void setValCaractList(ArrayList<ValuablePostcardCharacteristics> valCaractList) {
        this.valCaractList = valCaractList;
    }
}
