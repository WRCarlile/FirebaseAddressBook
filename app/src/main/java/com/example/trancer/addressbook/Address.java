package com.example.trancer.addressbook;

import org.parceler.Parcel;

/**
 * Created by Trancer on 9/25/16.
 */
@Parcel
public class Address {
    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String zip;

    public Address() {}

    public Address(String firstName, String lastName, String address, String city, String state, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
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
}

