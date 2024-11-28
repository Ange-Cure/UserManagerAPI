package com.angecure.api_users.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Users(@Id Integer userId, String userName, LocalDate birthdate, String countryOfResidence, String phoneNumber, Gender gender) {
    public enum Gender {
        Male, Female, Other, Prefer_not_to_say
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", birthdate=" + birthdate +
                ", countryOfResidence='" + countryOfResidence + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}
