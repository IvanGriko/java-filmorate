package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.AfterDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Validated
@Builder
public class User {

    private Long id;
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Имейл не указан или указан без @")
    private String email;
    @NotBlank(message = "Логин не указан или содержит пробел")
    private String login;
    private String name;
    @AfterDate(message = "Дата рождения указана неверно")
    private LocalDate birthday;
}
