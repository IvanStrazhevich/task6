package by.epam.task6.parser;

import by.epam.task6.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

public class DomBuilder extends XMLParserBuilder {
    private static Logger logger = LogManager.getLogger();
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Postcard> postcards = new ArrayList<>();
    private ArrayList<PostcardCharacteristics> charactList = new ArrayList<>();
    private ArrayList<ValuablePostcardCharacteristics> valCaractList = new ArrayList<>();
    private DocumentBuilder docBuilder;


    public DomBuilder() {
        // создание DOM-анализатора
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка конфигурации парсера: " + e);
        }
    }

    @Override
    public void buildPostcards(String fileName) {
        logger.info("Parsing with DOM");
        Document doc = null;
        try {
            // parsing XML-документа и создание древовидной структуры
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            // получение списка дочерних элементов <postcard>
            NodeList postcardList = root.getElementsByTagName(PostcardEnum.POSTCARD.getValue());
            for (int i = 0; i < postcardList.getLength(); i++) {
                Element postcardElement = (Element) postcardList.item(i);
                Postcard postcard = buildPostcard(postcardElement);
                postcards.add(postcard);
            }
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        } catch (SAXException e) {
            System.err.println("Parsing failure: " + e);
        }
    }

    private Postcard buildPostcard(Element postcardElement) {
        Postcard postcard = new Postcard();
        // заполнение объекта Postcard
        postcard.setCardType(CardType.valueOf(postcardElement.getAttribute(PostcardEnum.CARD_TYPE.getValue()).replace(' ', '_').toUpperCase()));
        postcard.setSent(Boolean.parseBoolean(postcardElement.getAttribute(PostcardEnum.SENT.getValue())));
        postcard.setPostcardId(postcardElement.getAttribute(PostcardEnum.POSTCARD_ID.getValue()));
        postcard.setTheme(Theme.valueOf(getElementTextContent(postcardElement, PostcardEnum.THEME.getValue()).replace(' ', '_').toUpperCase()));

        ValuablePostcardCharacteristics valPostcardCharacts = new ValuablePostcardCharacteristics();

        Element valPostcardCharsElement = (Element) postcardElement.getElementsByTagName(PostcardEnum.VALUABLE_POSTCARDS_CHARACTERISTICS.getValue()).item(0);
        valPostcardCharacts.setValuable(Valuable.valueOf(valPostcardCharsElement.getAttribute(PostcardEnum.VALUABLE.getValue()).toUpperCase()));
        valPostcardCharacts.setCountry(Country.valueOf(getElementTextContent(valPostcardCharsElement, PostcardEnum.COUNTRY.getValue())
                .replace(' ', '_').toUpperCase()));
        valPostcardCharacts.setYear(Year.parse(getElementTextContent(valPostcardCharsElement, PostcardEnum.YEAR.getValue())));

        Author author = new Author();

        Element authorElement = (Element) postcardElement.getElementsByTagName(PostcardEnum.AUTHOR.getValue()).item(0);
        author.setAuthorId(Integer.valueOf(postcard.getPostcardId().replace("card", "")));
        author.setAuthorName(getElementTextContent(authorElement, PostcardEnum.NAME.getValue()));
        author.setAuthorLastName(getElementTextContent(authorElement, PostcardEnum.LASTNAME.getValue()));
        valPostcardCharacts.setAuthorId(author.getAuthorId());
        valPostcardCharacts.setAuthor(author);
        valPostcardCharacts.setPostcardsCharacteristicsId(postcard.getPostcardId());

        postcard.setPostcardCharachteristics(valPostcardCharacts);
        authors.add(author);
        valCaractList.add(valPostcardCharacts);
        return postcard;
    }

    // получение текстового содержимого тега
    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
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

    public ArrayList<ValuablePostcardCharacteristics> getValCaractList() {
        return valCaractList;
    }

    public void setValCaractList(ArrayList<ValuablePostcardCharacteristics> valCaractList) {
        this.valCaractList = valCaractList;
    }
}
