package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Validated
@Builder
public class User {

    private long id;
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Имейл не указан или указан без @")
    private String email;
    @NotBlank(message = "Логин не указан")
    @Pattern(regexp = "\\S+", message = "Логин содержит пробел")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    private Set<Long> friends;

    public void addFriend(long friendId) {
        friends.add(friendId);
    }
}
