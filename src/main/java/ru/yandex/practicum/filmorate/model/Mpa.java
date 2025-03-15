package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Mpa {

    private int mpaId;
    private String name;

    public Mpa(int mpaId, String name) {
        this.mpaId = mpaId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mpa mpa = (Mpa) o;
        return mpaId == mpa.mpaId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mpaId);
    }
}
