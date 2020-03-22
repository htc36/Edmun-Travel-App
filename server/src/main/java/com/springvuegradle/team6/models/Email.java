package com.springvuegradle.team6.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Email {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @javax.validation.constraints.Email
    @Column(unique=true)
    private String address;

    @OneToOne(mappedBy = "email")
    private Profile profile;

    public Email() { }

    public Email(String email) {
        this.address = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String email) {
        this.address = email;
    }

    @Override
    public String toString() {
        return getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}