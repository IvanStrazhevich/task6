package by.epam.task6.parser;

import by.epam.task6.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

public class StAXBuilder extends XMLParserBuilder {
    private static Logger logger = LogManager.getLogger();
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Postcard> postcards = new ArrayList<>();
    private ArrayList<PostcardCharacteristics> charactList = new ArrayList<>();
    private ArrayList<ValuablePostcardCharacteristics> valCharactList = new ArrayList<>();
    private Postcard postcard;
    private XMLInputFactory inputFactory;

    public StAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }
@Override
    public void buildPostcards(String fileName) {
        logger.info("Parsing with STAX");
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (PostcardEnum.valueOf(name.toUpperCase().replace("-","_")).equals(PostcardEnum.POSTCARD)){
                        postcards.add(buildPostcard(reader));
                    }
                }
            }
        } catch (XMLStreamException ex) {
            logger.error("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            logger.error("File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("Impossible close file " + fileName + " : " + e);
            }
        }
    }

    private Postcard buildPostcard(XMLStreamReader reader) throws XMLStreamException {
        postcard = new Postcard();
        postcard.setPostcardId(reader.getAttributeValue(null, PostcardEnum.POSTCARD_ID.getValue()));
        postcard.setSent(Boolean.parseBoolean(reader
                .getAttributeValue(null, PostcardEnum.SENT.getValue()).toUpperCase()));
        postcard.setCardType(CardType.valueOf(reader
                .getAttributeValue(null, PostcardEnum.CARD_TYPE.getValue()).toUpperCase()));

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PostcardEnum.valueOf(name.toUpperCase().replace("-", "_"))) {
                        case THEME:
                            postcard.setTheme(Theme
                                    .valueOf(getXMLText(reader).replace(" ", "_").toUpperCase()));
                            break;
                        case POSTCARDS_CHARACTERISTICS:
                            postcard.setPostcardCharachteristics((ValuablePostcardCharacteristics) getXMLPostcardCharacts(reader));
                            break;
                        case VALUABLE_POSTCARDS_CHARACTERISTICS:
                            postcard.setPostcardCharachteristics(getXMLValPostcardCharacts(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PostcardEnum.valueOf(name.toUpperCase()) == PostcardEnum.POSTCARD) {
                        return postcard;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag postcard");
    }

    private PostcardCharacteristics getXMLPostcardCharacts(XMLStreamReader reader) throws XMLStreamException {
        PostcardCharacteristics postcardCharacteristics = new PostcardCharacteristics();
        postcardCharacteristics.setPostcardCharacteristicsId(postcard.getPostcardId());
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equalsIgnoreCase(PostcardEnum.COUNTRY.getValue())) {
                        postcardCharacteristics.setCountry(Country
                                .valueOf(getXMLText(reader).replace(" ", "_").toUpperCase()));
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PostcardEnum.valueOf(name.replace("-", "_").toUpperCase())
                            == PostcardEnum.POSTCARDS_CHARACTERISTICS) {
                        charactList.add(postcardCharacteristics);
                        return postcardCharacteristics;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag postcards-characteristics");
    }

    private ValuablePostcardCharacteristics getXMLValPostcardCharacts(XMLStreamReader reader) throws
            XMLStreamException {
        ValuablePostcardCharacteristics valuablePostcardCharacteristics = new ValuablePostcardCharacteristics();
        valuablePostcardCharacteristics.setPostcardsCharacteristicsId(postcard.getPostcardId());
        valuablePostcardCharacteristics
                .setValuable(Valuable.valueOf(reader.getAttributeValue(null,
                        PostcardEnum.VALUABLE.getValue()).toUpperCase()));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PostcardEnum.valueOf(name.toUpperCase())) {
                        case COUNTRY:
                            valuablePostcardCharacteristics.setCountry(Country
                                    .valueOf(getXMLText(reader).replace(" ", "_").toUpperCase()));
                            break;
                        case YEAR:
                            valuablePostcardCharacteristics.setYear(Year.parse(getXMLText(reader)));
                            break;
                        case AUTHOR:
                            Author author = getXMLAuthor(reader);
                            valuablePostcardCharacteristics.setAuthorId(author.getAuthorId());
                            valuablePostcardCharacteristics.setAuthor(author);
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PostcardEnum.valueOf(name.replace("-", "_").toUpperCase())
                            == PostcardEnum.VALUABLE_POSTCARDS_CHARACTERISTICS) {
                        valCharactList.add(valuablePostcardCharacteristics);
                        return valuablePostcardCharacteristics;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag valuable-postcards-characteristics");
    }

    private Author getXMLAuthor(XMLStreamReader reader) throws XMLStreamException {
        Author author = new Author();
        author.setAuthorId(Integer.valueOf(postcard.getPostcardId().replace("card", "")));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PostcardEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            author.setAuthorName(getXMLText(reader));
                            break;
                        case LASTNAME:
                            author.setAuthorLastName(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PostcardEnum.valueOf(name.toUpperCase()) == PostcardEnum.AUTHOR) {
                        authors.add(author);
                        return author;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Author");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    public ArrayList<Author> findAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Postcard> findPostcards() {
        return postcards;
    }

    public void setPostcards(ArrayList<Postcard> postcards) {
        this.postcards = postcards;
    }

    public ArrayList<PostcardCharacteristics> findCharactList() {
        return charactList;
    }

    public void setCharactList(ArrayList<PostcardCharacteristics> charactList) {
        this.charactList = charactList;
    }

    public ArrayList<ValuablePostcardCharacteristics> findValCharactList() {
        return valCharactList;
    }

    public void setValCharactList(ArrayList<ValuablePostcardCharacteristics> valCharactList) {
        this.valCharactList = valCharactList;
    }
}

