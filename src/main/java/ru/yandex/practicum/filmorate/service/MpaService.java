package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaRepository mpaRepository;

    public Mpa getMpaById(Integer mpaId) {
        return mpaRepository.getMpaById(mpaId);
    }

    public List<Mpa> getAllMpa() {
        return mpaRepository.getAllMpa();
    }

    public Mpa createMpa(Mpa mpa) {
        return mpaRepository.createMpa(mpa);
    }

    public Mpa updateMpa(Mpa mpa) {
        return mpaRepository.updateMpa(mpa);
    }

}
