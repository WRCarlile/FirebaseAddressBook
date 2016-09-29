package com.example.trancer.addressbook.models;

import org.parceler.Parcel;

@Parcel
public class Address {
    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String zip;
    String birthDate;

    public Address() {}

    public Address(String firstName, String lastName, String address, String city, String state, String zip, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {return city;}

    public String getZip() {
        return zip;
    }

    public String getState() {return state; }

    public String getBirthDate() {return birthDate; }
}

