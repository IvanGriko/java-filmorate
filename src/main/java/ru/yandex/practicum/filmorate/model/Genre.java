package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Genre {

    private int genreId;
    private String name;

    public Genre(int genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return genreId == genre.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(genreId);
    }
}

