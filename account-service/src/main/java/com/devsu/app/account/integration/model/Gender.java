package com.devsu.app.account.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum Gender {
    @JsonProperty("Male")
    MALE("Male"), @JsonProperty("Female")
    FEMALE("Female"), @JsonProperty("Other")
    OTHER("Other");

    private String genderValue;

    private Gender(String genderValue) {
        this.genderValue = genderValue;
    }

    public static Gender getGenderFromValueString(String genderValue) {
        genderValue = genderValue.toUpperCase();

        return switch (genderValue) {
        case "MALE" -> MALE;
        case "FEMALE" -> FEMALE;
        default -> OTHER;
        };
    }

    @JsonCreator
    public static Gender fromString(String strValue) {
        return getGenderFromValueString(strValue);
    }

}
