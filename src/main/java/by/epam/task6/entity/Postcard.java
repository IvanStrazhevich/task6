package by.epam.task6.entity;

public class Postcard extends Entity{
    private int postcardId;
    private Theme theme;
    private CardType cardType;
    private boolean sent;
    private String postcardCharachteristics;

    public Postcard() {
    }

    public Postcard(int id, Theme theme, CardType cardType, boolean sent, String postcardCharachteristics) {
        super(id);
        this.postcardId = id;
        this.theme = theme;
        this.cardType = cardType;
        this.sent = sent;
        this.postcardCharachteristics = postcardCharachteristics;
    }

    public int getPostcardId() {
        return postcardId;
    }

    public void setPostcardId(int postcardId) {
        this.postcardId = postcardId;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getPostcardCharachteristics() {
        return postcardCharachteristics;
    }

    public void setPostcardCharachteristics(String postcardCharachteristics) {
        this.postcardCharachteristics = postcardCharachteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Postcard postcard = (Postcard) o;

        if (postcardId != postcard.postcardId) return false;
        if (sent != postcard.sent) return false;
        if (theme != postcard.theme) return false;
        if (cardType != postcard.cardType) return false;
        return postcardCharachteristics != null ? postcardCharachteristics.equals(postcard.postcardCharachteristics) : postcard.postcardCharachteristics == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + postcardId;
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (sent ? 1 : 0);
        result = 31 * result + (postcardCharachteristics != null ? postcardCharachteristics.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Postcard{" +
                "postcardId=" + postcardId +
                ", theme=" + theme +
                ", cardType=" + cardType +
                ", sent=" + sent +
                ", postcardCharachteristics='" + postcardCharachteristics + '\'' +
                '}';
    }
}
