package by.epam.task6.entity;

public enum Theme {
    CITY_SCAPE("City Scape"), NATURE("Nature"),
    PEOPLE("People"), RELIGION("Religion"),
    SPORT("Sport"), ARCHITECTURE("Architecture"),
    ART("Art"), TRANSPORT("Transport");
    private String theme;

    Theme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return theme;
    }
}
