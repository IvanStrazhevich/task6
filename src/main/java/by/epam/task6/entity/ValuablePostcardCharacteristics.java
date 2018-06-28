package by.epam.task6.entity;

import java.time.Year;

public class ValuablePostcardCharacteristics extends PostcardCharacteristics {
    private String postcardsCharacteristicsId;
    private Year year;
    private Valuable valuable;
    private Author author;
    private int authorId;

    public ValuablePostcardCharacteristics() {
    }

    public ValuablePostcardCharacteristics(int id, String postcardCharacteristicsId, Country country,
                                           String postcardsCharacteristicsId, Year year, Valuable valuable,
                                           Author author, int authorId) {
        super(id, postcardCharacteristicsId, country);
        this.postcardsCharacteristicsId = postcardsCharacteristicsId;
        this.year = year;
        this.valuable = valuable;
        this.author = author;
        this.authorId = authorId;
    }

    public String getPostcardsCharacteristicsId() {
        return postcardsCharacteristicsId;
    }

    public void setPostcardsCharacteristicsId(String postcardsCharacteristicsId) {
        this.postcardsCharacteristicsId = postcardsCharacteristicsId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Valuable getValuable() {
        return valuable;
    }

    public void setValuable(Valuable valuable) {
        this.valuable = valuable;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ValuablePostcardCharacteristics that = (ValuablePostcardCharacteristics) o;

        if (authorId != that.authorId) return false;
        if (postcardsCharacteristicsId != null ? !postcardsCharacteristicsId.equals(that.postcardsCharacteristicsId) : that.postcardsCharacteristicsId != null)
            return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (valuable != that.valuable) return false;
        return author != null ? author.equals(that.author) : that.author == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (postcardsCharacteristicsId != null ? postcardsCharacteristicsId.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (valuable != null ? valuable.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + authorId;
        return result;
    }

    @Override
    public String toString() {
        return "ValuablePostcardCharacteristics{" +
                "postcardsCharacteristicsId='" + postcardsCharacteristicsId + '\'' +
                ", year=" + year +
                ", valuable=" + valuable +
                ", author=" + author +
                '}';
    }
}
