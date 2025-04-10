package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.AfterDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class Film {

    private long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длинна описания - 200 символов")
    private String description;
    @AfterDate(message = "Дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть меньше 0")
    private Integer duration;
    private Set<Long> likes = new HashSet<>();
    private List<Genre> genres = new ArrayList<>();
    @NonNull
    private Mpa mpa;

    public List<Genre> getGenres() {
        Set<Genre> uniqueGenres = new HashSet<>(genres);
        return new ArrayList<>(uniqueGenres);
    }
}
