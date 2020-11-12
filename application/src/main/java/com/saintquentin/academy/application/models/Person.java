package com.saintquentin.academy.customannotations.models;

import com.saintquentin.academy.customannotations.annotations.interfaces.Builder;
import com.saintquentin.academy.customannotations.annotations.interfaces.Format;
import com.saintquentin.academy.customannotations.annotations.interfaces.JsonElement;
import com.saintquentin.academy.customannotations.annotations.interfaces.JsonSerializable;

@JsonSerializable
public class Person {
    @JsonElement
    private String lastName;
    @JsonElement
    private String firstName;
    @JsonElement(key = "personAge")
    private String age;
    private String address;

    public Person(String lastName, String firstName, String age, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.address = address;
    }

    public Person(String lastName, String firstName, String age) {
        this(lastName, firstName, age, null);
    }

    @Format
    private void formatNames() {
        this.lastName = this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1);
        this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1);
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getAge() {
        return this.age;
    }

    public String getAddress() {
        return this.address;
    }

    @Builder
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Builder
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Builder
    public void setAge(String age) {
        this.age = age;
    }

    @Builder
    public void setAddress(String address) {
        this.address = address;
    }
}
