package by.epam.task6.entity;

public class PostcardCharacteristics extends Entity{
    private String postcardCharacteristicsId;
    private Country country;

    public PostcardCharacteristics() {
    }

    public PostcardCharacteristics(int id, String postcardCharacteristicsId, Country country) {
        super(id);
        this.postcardCharacteristicsId = postcardCharacteristicsId;
        this.country = country;
    }

    public String getPostcardCharacteristicsId() {
        return postcardCharacteristicsId;
    }

    public void setPostcardCharacteristicsId(String postcardCharacteristicsId) {
        this.postcardCharacteristicsId = postcardCharacteristicsId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PostcardCharacteristics that = (PostcardCharacteristics) o;

        if (postcardCharacteristicsId != null ? !postcardCharacteristicsId.equals(that.postcardCharacteristicsId) : that.postcardCharacteristicsId != null)
            return false;
        return country == that.country;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (postcardCharacteristicsId != null ? postcardCharacteristicsId.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostcardCharacteristics{" +
                "postcardCharacteristicsId='" + postcardCharacteristicsId + '\'' +
                ", country=" + country +
                '}';
    }
}
