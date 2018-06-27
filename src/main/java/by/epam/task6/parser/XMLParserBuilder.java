package by.epam.task6.parser;

import by.epam.task6.entity.Author;
import by.epam.task6.entity.Postcard;
import by.epam.task6.entity.PostcardCharacteristics;
import by.epam.task6.entity.ValuablePostcardCharacteristics;

import java.util.ArrayList;

public abstract class XMLParserBuilder {
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Postcard> postcards = new ArrayList<>();
    private ArrayList<PostcardCharacteristics> charactList = new ArrayList<>();
    private ArrayList<ValuablePostcardCharacteristics> valCharactList = new ArrayList<>();

    public XMLParserBuilder(ArrayList<Author> authors, ArrayList<Postcard> postcards,
                            ArrayList<PostcardCharacteristics> charactList,
                            ArrayList<ValuablePostcardCharacteristics> valCharactList) {
        this.authors = authors;
        this.postcards = postcards;
        this.charactList = charactList;
        this.valCharactList = valCharactList;
    }

    public XMLParserBuilder() {
    }

    public ArrayList<Author> findAuthors() {
        return authors;
    }

    public ArrayList<Postcard> findPostcards() {
        return postcards;
    }

    public ArrayList<PostcardCharacteristics> findCharactList() {
        return charactList;
    }

    public ArrayList<ValuablePostcardCharacteristics> findValCharactList() {
        return valCharactList;
    }
    abstract public void buildPostcards(String filename);
}
