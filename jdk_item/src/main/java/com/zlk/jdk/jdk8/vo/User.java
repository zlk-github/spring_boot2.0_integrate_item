package com.zlk.jdk.jdk8.vo;

import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class User {
    private String name;

    private String city;

    private Integer age;

    public Optional<String> getCity() {
        return Optional.ofNullable(city);
    }

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(city, user.city) && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, age);
    }*/
}