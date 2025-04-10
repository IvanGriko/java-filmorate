package ru.yandex.practicum.filmorate.storage.mpa;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MpaRepository {

    private final JdbcTemplate jdbcTemplate;

    public Mpa getMpaById(Integer mpaId) {
        if (mpaId < 1 || !getAllMpa().contains(new Mpa(mpaId, ""))) {
            throw new NotFoundException("mpaId is not found");
        }
        String sql = "SELECT * FROM mpa_ratings WHERE mpa_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), mpaId)
                .stream().findAny().orElse(null);
    }

    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM mpa_ratings ORDER BY mpa_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    public Mpa createMpa(Mpa mpa) {
        String sql = "SELECT * FROM mpa_ratings WHERE mpa_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), mpa.getName())
                .stream().findAny().orElse(null);
    }

    public Mpa updateMpa(Mpa mpa) {
        if (getMpaById(mpa.getId()) == null) {
            throw new EntityNotFoundException("MPA не найден для обновления");
        }
        String sql = "UPDATE mpa_ratings SET name = ? WHERE mpa_id = ?";
        jdbcTemplate.update(sql, mpa.getName(), mpa.getId());
        return mpa;
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return new Mpa(rs.getInt("mpa_id"), rs.getString("name"));
    }

//    public List<Mpa> getAllMpa() {
//        String sql = "SELECT * FROM mpa_ratings ORDER BY mpa_id";
//        return jdbcTemplate.query(sql, this::mapRowToRating);
//    }
//
//    public Optional<Mpa> getMpaById(int mpa_id) {
//        String sql = "SELECT * FROM mpa_ratings WHERE mpa_id = ?";
//        List<Mpa> result = jdbcTemplate.query(sql, this::mapRowToRating, mpa_id);
//        return result.stream().findFirst();
//    }
//
//    private Mpa mapRowToRating(ResultSet rs, int rowNum) throws SQLException {
//        return new Mpa(rs.getInt("mpa_id"), rs.getString("name"));
//    }
//
//    public Mpa createMpa(Mpa mpa) {
//        String sql = "SELECT * FROM mpa_ratings WHERE mpa_id = ?";
//        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToRating(rs, rowNum), mpa.getName())
//                .stream().findAny().orElse(null);
//    }
}