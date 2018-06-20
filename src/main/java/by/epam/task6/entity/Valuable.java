package by.epam.task6.entity;

public enum Valuable {
    COLLECTION("collection"), HISTORICAL("historical"), THEMATIC("thematic");
    private String valueType;

    Valuable(String valueType) {
        this.valueType = valueType;
    }
}
