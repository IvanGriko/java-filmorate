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
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

@AutoConfigureTestDatabase
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class, FilmDbStorage.class, MpaDbStorage.class})
class FilmorateApplicationTests {

    @Autowired
    private final UserDbStorage userDbStorage;
    @Autowired
    private final FilmDbStorage filmDbStorage;
    @Autowired
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void createUserTest() {
        User user = User.builder()
                .email("userfbgfdg@email.com")
                .name("Userdfeg")
                .login("loginurtyhb")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        user.setId(userDbStorage.getUserByName("Userdfeg").getId());
        User createdUser = userDbStorage.getUserByName("Userdfeg");
        Assertions.assertEquals(user, createdUser);
    }

    @Test
    public void getUserTest() {
        User user = User.builder()
                .email("user1fvr@email.com")
                .name("Userrgfersdv")
                .login("logingxfgjhg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        long createdUserId = userDbStorage.getUserByName("Userrgfersdv").getId();
        User userOptional = userDbStorage.getUserById(createdUserId);
        Assertions.assertEquals(userOptional.getName(), "Userrgfersdv",
                "Actual user is not expected");
    }

    @Test
    public void getUsersTest() {
        User user1 = User.builder()
                .email("user2fbgtry@email.com")
                .name("User1")
                .login("login1kjhgxg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user3trhytt@email.com")
                .name("User2")
                .login("login2ghfxfhgjh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        Assertions.assertFalse(userDbStorage.getUsers().isEmpty(),
                "Users list is empty");
    }

    @Test
    public void updateUserTest() {
        User user = User.builder()
                .email("userthrgv@email.com")
                .name("Userrstikjh")
                .login("loginukjhfdfx")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        user.setEmail("updateduser@email.com");
        user.setName("Updated User");
        User createdUser = userDbStorage.getUserByName("Userrstikjh");
        user.setId(createdUser.getId());
        userDbStorage.updateUser(user);
        User updatedUser = userDbStorage.getUserByName("Updated User");
        Assertions.assertSame(updatedUser, user,
                "Actual user is not expected updated user");
    }

    @Test
    public void addFriendTest() {
        User user1 = User.builder()
                .email("user1rtgvtrd@email.com")
                .name("User1")
                .login("login1dtjh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dgrtv@email.com")
                .name("User2")
                .login("login2kjhsdd")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        Assertions.assertFalse(userDbStorage.getFriends(1L).contains(userDbStorage.getUserById(2L)),
                "Friend is not added");
    }

    @Test
    public void getFriendsTest() {
        User user = User.builder()
                .email("userrgvre@email.com")
                .name("User")
                .login("loginthdfgx")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1fgvfredf@email.com")
                .name("User1")
                .login("login1dfjh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2ver@email.com")
                .name("User2")
                .login("login2gjhfdf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(1L, 3L);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUserById(2L));
        expectedFriends.add(userDbStorage.getUserById(3L));
        Set<User> actualFriends = userDbStorage.getFriends(1L);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeFriendTest() {
        User user = User.builder()
                .email("userergverd@email.com")
                .name("User")
                .login("loginhtdhgd")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1fvrtd@email.com")
                .name("User1")
                .login("login1yjhytg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2ergdfv@email.com")
                .name("User2")
                .login("login2tjuyt")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(1L, 3L);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUserById(3L));
        userDbStorage.removeFriend(1L, 2L);
        Set<User> actualFriends = userDbStorage.getFriends(1L);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeAllFriendsTest() {
        User user = User.builder()
                .email("usergfrvedg@email.com")
                .name("User")
                .login("logingfjhgdf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1ergverdx@email.com")
                .name("User1")
                .login("login1dfhb")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dfvcrgv@email.com")
                .name("User2")
                .login("login2jhytfgd")
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
                .email("useruyjnhb@email.com")
                .name("User")
                .login("loginfhgtrh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1thgf@email.com")
                .name("User1")
                .login("login1jhrdfg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2fghnu@email.com")
                .name("User2")
                .login("login2kjhdfg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.addFriend(1L, 2L);
        userDbStorage.addFriend(3L, 2L);
        Assertions.assertTrue(userDbStorage.getCommonFriends(2L, 3L).contains(userDbStorage.getUserById(2L)),
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
                .email("useryfgjhd@email.com")
                .name("User")
                .login("logindfhbgv")
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
                .email("user1dfgrtgjh@email.com")
                .name("User1")
                .login("login1dxfgj")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dfhgyh@email.com")
                .name("User2")
                .login("login2fxyhdjk")
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
                .email("user1dfhntyjg@email.com")
                .name("User1")
                .login("login1hkjf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dhbtfgjhb@email.com")
                .name("User2")
                .login("login2hkgmv")
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
                .email("userfghfcgyg@email.com")
                .name("User")
                .login("loginvkhm")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1jkiukhj@email.com")
                .name("User1")
                .login("login1jhkjn")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2ukiugfgc@email.com")
                .name("User2")
                .login("login2hjij")
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