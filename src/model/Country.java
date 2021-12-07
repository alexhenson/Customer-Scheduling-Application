package model;

/** This class is responsible for the functionality of the Country class. */
public class Country {
    private int countryId;
    private String countryName;

    /** This constructor initializes the fields from the two parameters.
     *   @param countryId to set countryId
     *   @param countryName to set countryName
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return countryId + ". " + countryName;
    }
}
