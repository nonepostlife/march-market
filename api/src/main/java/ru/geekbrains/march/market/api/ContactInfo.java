package ru.geekbrains.march.market.api;

public class ContactInfo {
    private String country;
    private String region;
    private String zip;
    private String city;
    private String address;
    private String phone;
    private String additionalInformation;

    public ContactInfo() {
    }

    public ContactInfo(String country, String region, String zip, String city, String address, String phone, String additionalInformation) {
        this.country = country;
        this.region = region;
        this.zip = zip;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.additionalInformation = additionalInformation;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                '}';
    }
}
