package ru.itone.beeline.database.entity;

import org.joda.time.DateTime;

import com.github.javafaker.Faker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWebVisit {

    private static final Faker faker = new Faker();

    private String login;
    private String location;
    private DateTime dateTime;

    public static UserWebVisit random() {
        return UserWebVisit.builder()
            .login(faker.name().username())
            .location("https://" + faker.internet().url())
            .dateTime(DateTime.now())
            .build();
    }
}
