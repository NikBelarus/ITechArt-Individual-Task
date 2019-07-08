package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("street")
    private String street;

    @JsonProperty("homeNum")
    private String homeNum;

    @JsonProperty("apartment")
    private String apartment;

    @JsonProperty("postcode")
    private String postcode;

    public Address() {}

    public Address(String country, String city, String street, String homeNum, String apartment, String postcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.homeNum = homeNum;
        this.apartment = apartment;
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHomeNum() {
        return homeNum;
    }

    public void setHomeNum(String homeNum) {
        this.homeNum = homeNum;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNum=" + homeNum +
                ", apartment=" + apartment +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
