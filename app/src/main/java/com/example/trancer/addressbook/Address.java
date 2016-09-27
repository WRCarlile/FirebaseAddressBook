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
    String zip;

    public Address() {}

    public Address(String firstName, String lastName, String address, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getFirstName() {
        return firstName;

    }
}

