package by.epam.task6.entity;

public enum Country {
    BELARUS("Belarus"), GREATE_BRITAIN("Great Britain"), USA("USA"),
    PHILIPPINE("Philippine"), AUSTRALIA("Australia"),
    NEW_ZEALAND("NewZealand"), CHINA("China"), JAPAN("Japan");
    private String country;

    Country(String country) {
        this.country = country;
    }
}
