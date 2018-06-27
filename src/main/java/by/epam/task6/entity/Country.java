package by.epam.task6.entity;

public enum Country {
    BELARUS("Belarus"), GREAT_BRITAIN("Great Britain"), USA("USA"),
    PHILIPPINE("Philippine"), AUSTRALIA("Australia"),
    NEW_ZEALAND("New Zealand"), CHINA("China"), JAPAN("Japan");
    private String country;

    Country(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }
}
