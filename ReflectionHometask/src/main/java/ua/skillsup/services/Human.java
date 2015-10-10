package ua.skillsup.services;

import ua.skillsup.annotations.*;
import java.time.LocalDate;

/**
 * Created by Honey on 16.09.2015.
 */

public class Human {
    private String firstName;
    private String lastName;

    @JsonValue(name = "fun")
    private String hobby;

    @CustomDateFormat(format = "ddMMyyyy")
    private LocalDate birthDate;

    public Human() {
        this.firstName = null;
        this.lastName = null;
        this.hobby = null;
        this.birthDate = null;
    }

    public Human(String firstName, String lastName, String hobby, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobby = hobby;
        this.birthDate = birthDate;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return  "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hobby='" + hobby + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
