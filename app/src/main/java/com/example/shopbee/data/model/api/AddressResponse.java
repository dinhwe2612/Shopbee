package com.example.shopbee.data.model.api;

public class AddressResponse {
    private String name;
    private String address;
    private String city;
    private String country;
    private String state;
    private String zip_code;
    private Boolean def;

    public AddressResponse(){}

    public AddressResponse(String address, String city, String state, String country, String zip_code, Boolean def, String name) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip_code = zip_code;
        this.def = def;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {return address;}
    public String getCity() {
        return city;
    }
    public String getZip_code() {
        return zip_code;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Boolean getDef() {
        return def;
    }

    public void setDef(Boolean def) {
        this.def = def;
    }

    public String toString() {
        return address + " ," + city + " ," + zip_code + " ," + state +  " ," + country;
    }
}
