package by.epam.task6.entity;

public enum CardType {
    ORDINARY("ordinary"),CONGRATULATIONS("congratulations"),
    ADVERTISING("advertising");
    private String cardType;

    CardType(String cardType) {
        this.cardType = cardType;
    }
}
