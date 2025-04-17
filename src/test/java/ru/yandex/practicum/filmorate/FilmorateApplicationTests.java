package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

@AutoConfigureTestDatabase
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class, FilmDbStorage.class, MpaDbStorage.class, GenreDbStorage.class})
class FilmorateApplicationTests {

    @Autowired
    private final UserDbStorage userDbStorage;
    @Autowired
    private final FilmDbStorage filmDbStorage;
    @Autowired
    private final MpaDbStorage mpaDbStorage;
    @Autowired
    private final GenreStorage genreStorage;

    @Test
    public void createUserTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        user.setId(1);
        Assertions.assertSame(userDbStorage.getUser(1), user,
                "Actual user is not expected");
    }

    @Test
    public void getUserTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        User userOptional = userDbStorage.getUser(1);
        Assertions.assertEquals(userOptional.getId(), 1,
                "Actual userId is not expected");
    }

    @Test
    public void getUsersTest() {
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        Assertions.assertEquals(userDbStorage.getUsers().size(), 2,
                "Actual users number is not expected");
    }

    @Test
    public void updateUserTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        user.setEmail("updateduser@email.com");
        user.setName("Updated User");
        user.setId(1);
        userDbStorage.updateUser(user);
        Assertions.assertSame(userDbStorage.getUser(1), user,
                "Actual user is not expected updated user");
    }

    @Test
    public void addFriendTest() {
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        Assertions.assertFalse(userDbStorage.getFriends(1L).contains(userDbStorage.getUser(2L)),
                "Friend is not added");
    }

    @Test
    public void getFriendsTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(1L, 3L);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUser(2L));
        expectedFriends.add(userDbStorage.getUser(3L));
        Set<User> actualFriends = userDbStorage.getFriends(1L);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeFriendTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(1L, 3L);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUser(3L));
        userDbStorage.removeFriend(1L, 2L);
        Set<User> actualFriends = userDbStorage.getFriends(1L);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeAllFriendsTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(1L, 3L);
        userDbStorage.removeAllFriends(1L);
        Assertions.assertTrue(userDbStorage.getFriends(1L).isEmpty(),
                "Friends set is not empty");
    }

    @Test
    public void getCommonFriendsTest() {
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(3L, 2L);
        Assertions.assertTrue(userDbStorage.getCommonFriends(2L, 3L).contains(userDbStorage.getUser(2L)),
                "Set of common friends is not expected");
    }

    @Test
    public void createFilmTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmDescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        Assertions.assertNotNull(filmDbStorage.getFilm(1),
                "Film is not found in database");
    }

    @Test
    public void getFilmTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmDescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        film.setId(1);
        Assertions.assertSame(filmDbStorage.getFilm(1), film,
                "Actual film is not expected");
    }

    @Test
    public void getFilmsTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film1 = Film.builder()
                .name("film1")
                .description("film1description")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film film2 = Film.builder()
                .name("film2")
                .description("film2description")
                .duration(120)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film1);
        filmDbStorage.createFilm(film2);
        Assertions.assertEquals(filmDbStorage.getFilms().size(), 2,
                "Films are not found");
    }

    @Test
    public void updateFilmTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmdescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        film.setId(1);
        film.setName("UpdatedFilm");
        film.setDescription("Updated film description");
        filmDbStorage.updateFilm(film);
        Assertions.assertSame(filmDbStorage.getFilm(1), film,
                "Actual film is not expected");
    }

    @Test
    public void addLikeTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("description")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        filmDbStorage.addLike(1, 1);
        Assertions.assertFalse(filmDbStorage.getLikes(1).isEmpty(),
                "Like is not found");
    }

    @Test
    public void getLikesTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmdescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        filmDbStorage.addLike(1, 1);
        filmDbStorage.addLike(1, 2);
        Set<Long> expectedLikesSet = new HashSet<>(Set.of(1L, 2L));
        Assertions.assertEquals(expectedLikesSet, filmDbStorage.getLikes(1),
                "Actual likes set is not expected");
    }

    @Test
    public void removeLikeTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmdescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();

        filmDbStorage.createFilm(film);
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        filmDbStorage.addLike(1, 1);
        filmDbStorage.addLike(1, 2);
        Set<Long> expectedLikesSet = new HashSet<>(Set.of(2L));
        filmDbStorage.removeLike(1, 1);
        Assertions.assertEquals(expectedLikesSet, filmDbStorage.getLikes(1),
                "Actual likes set is not expected");
    }

    @Test
    public void getPopularFilmsTest() {
        Mpa mpa;
        if (mpaDbStorage.getMpaById(1) == null) {
            mpa = new Mpa(1, "G");
        } else {
            mpa = mpaDbStorage.getMpaById(1);
        }
        Film film = Film.builder()
                .name("film")
                .description("filmdescription")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film film1 = Film.builder()
                .name("film1")
                .description("film1description")
                .duration(100)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film film2 = Film.builder()
                .name("film2")
                .description("film2description")
                .duration(120)
                .mpa(mpa)
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        filmDbStorage.createFilm(film1);
        filmDbStorage.createFilm(film2);
        User user = User.builder()
                .name("User")
                .login("login")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .name("User1")
                .login("login1")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .name("User2")
                .login("login2")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        filmDbStorage.addLike(2, 1);
        filmDbStorage.addLike(2, 2);
        filmDbStorage.addLike(3, 1);
        filmDbStorage.addLike(3, 2);
        filmDbStorage.addLike(3, 3);
        List<Film> expectedTopLikesList = new ArrayList<>(List.of(film2, film1));
        Assertions.assertEquals(expectedTopLikesList, filmDbStorage.getPopularFilms(2),
                "Actual toplist is not expected");
    }

}