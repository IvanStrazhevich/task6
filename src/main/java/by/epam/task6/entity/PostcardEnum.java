package by.epam.task6.entity;

public enum PostcardEnum {

    POSTCARDS("postcards"),
    POSTCARD("Postcard"),
    CARD_TYPE("card-type"), SENT("sent"),POSTCARD_ID("postcardId"),
    THEME("theme"),
    POSTCARDS_CHARACTERISTICS("postcards-characteristics"),
    VALUABLE_POSTCARDS_CHARACTERISTICS("valuable-postcards-characteristics"),
    VALUABLE("valuable"),
    COUNTRY("country"), YEAR("year"), AUTHOR("author"),
    NAME("name"),
    LASTNAME("lastname");
    private String value;

    PostcardEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
