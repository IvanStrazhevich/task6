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
    private static final String CARD_ID_PREFIX = "card";
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Postcard> postcards = new ArrayList<>();
    private ArrayList<PostcardCharacteristics> charactList = new ArrayList<>();
    private ArrayList<ValuablePostcardCharacteristics> valCaractList = new ArrayList<>();
    private ValuablePostcardCharacteristics valuablePostcardCharacteristics;
    private Author author;
    private Postcard postcard;
    private PostcardCharacteristics postcardCharacteristics;


    private boolean bTheme = false;
    private boolean bCountry = false;
    private boolean bYear = false;
    private boolean bAuthor = false;
    private boolean bName = false;
    private boolean bLastName = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        switch (PostcardEnum.valueOf(qName.toUpperCase().toUpperCase()
                .replace("-", "_").replace(" ", "_"))) {
            case POSTCARD:
                postcard = new Postcard();
                String cardTypeValue = attributes.getValue(PostcardEnum.CARD_TYPE.getValue());
                CardType cardType = CardType.valueOf(cardTypeValue.toUpperCase());
                postcard.setCardType(cardType);
                postcard.setSent(Boolean.parseBoolean(attributes.getValue(PostcardEnum.SENT.getValue())));
                postcard.setPostcardId(attributes.getValue(PostcardEnum.POSTCARD_ID.getValue()));
                break;
            case THEME:
                bTheme = true;
                break;
            case POSTCARDS_CHARACTERISTICS:
                postcardCharacteristics = new PostcardCharacteristics();
                break;
            case VALUABLE_POSTCARDS_CHARACTERISTICS:
                valuablePostcardCharacteristics = new ValuablePostcardCharacteristics();
                String valuableValue = attributes.getValue(PostcardEnum.VALUABLE.getValue());
                Valuable valuable = Valuable.valueOf(valuableValue.toUpperCase());
                valuablePostcardCharacteristics.setValuable(valuable);
                break;
            case COUNTRY:
                bCountry = true;
                break;
            case YEAR:
                bYear = true;
                break;
            case AUTHOR:
                author = new Author();
                bAuthor = true;
                break;
            case NAME:
                bName = true;
                break;
            case LASTNAME:
                bLastName = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        switch (PostcardEnum.valueOf(qName.toUpperCase().toUpperCase()
                .replace("-", "_").replace(" ", "_"))) {
            case POSTCARD:
                postcards.add(postcard);
                break;
            case POSTCARDS_CHARACTERISTICS:
                postcardCharacteristics.setPostcardCharacteristicsId(postcard.getPostcardId());
                postcard.setPostcardCharachteristics((ValuablePostcardCharacteristics)postcardCharacteristics);
                charactList.add(postcardCharacteristics);
                break;
            case VALUABLE_POSTCARDS_CHARACTERISTICS:
                valuablePostcardCharacteristics.setPostcardsCharacteristicsId(postcard.getPostcardId());
                postcard.setPostcardCharachteristics(valuablePostcardCharacteristics);
                valCaractList.add(valuablePostcardCharacteristics);
                break;
            case AUTHOR:
                authors.add(author);
                valuablePostcardCharacteristics.setAuthorId(author.getAuthorId());
                valuablePostcardCharacteristics.setAuthor(author);
                break;
            default:
                break;
        }
    }


    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bTheme) {
            postcard.setTheme(Theme.valueOf(new String(ch, start, length).toUpperCase().replace(" ", "_")));
            bTheme = false;
        } else if (bCountry) {
            valuablePostcardCharacteristics.setCountry(Country.valueOf(new String(ch, start, length).replace(" ", "_").toUpperCase()));
            bCountry = false;
        } else if (bYear) {
            valuablePostcardCharacteristics.setYear(Year.parse(new String(ch, start, length)));
            bYear = false;
        } else if (bAuthor) {
            author.setAuthorId(Integer.valueOf(postcard.getPostcardId().replace(CARD_ID_PREFIX, "")));
            bAuthor = false;
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
