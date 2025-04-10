package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class MpaController {

    @Autowired
    private final MpaService mpaService;

    @GetMapping("/mpa/{id}")
    public Mpa findById(@PathVariable Integer id) {
        return mpaService.getMpaById(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> findAll() {
        return mpaService.getAllMpa();
    }

    @PostMapping("/mpa")
    public Mpa create(@Valid @RequestBody Mpa mpa) {
        return mpaService.createMpa(mpa);
    }

    @PutMapping("/mpa")
    public Mpa update(@Valid @RequestBody Mpa mpa) {
        return mpaService.updateMpa(mpa);
    }

//    @PostMapping("/mpa")
//    public Mpa create(@Valid @RequestBody Mpa mpa) {
//        return mpaService.createMpa(mpa);
//    }
//
//    @GetMapping("/mpa")
//    public List<Mpa> getAllMpa() {
//        return mpaService.getAllMpa();
//    }
//
//    @GetMapping("/mpa/{id}")
//    public Mpa getMpaById(@PathVariable int id) {
//        return mpaService.getMpaById(id);
//    }

}
