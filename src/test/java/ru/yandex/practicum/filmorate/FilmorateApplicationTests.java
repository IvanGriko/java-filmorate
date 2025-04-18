package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
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
        User getUser = userDbStorage.getUserById(createdUserId);
        Assertions.assertEquals(getUser.getName(), "Userrgfersdv",
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
        User createdUser = userDbStorage.getUserByName("Userrstikjh");
        user.setId(createdUser.getId());
        user.setName("Updated User");
        userDbStorage.updateUser(user);
        User updatedUser = userDbStorage.getUserById(user.getId());
        Assertions.assertEquals("Updated User", updatedUser.getName(),
                "Actual user is not updated");
    }

    @Test
    public void addFriendTest() {
        User user1 = User.builder()
                .email("user1rtgvtrd@email.com")
                .name("Usergnhgf")
                .login("login1dtjh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dgrtv@email.com")
                .name("Userfhftgjht")
                .login("login2kjhsdd")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long userId = userDbStorage.getUserByName("Usergnhgf").getId();
        long friendId = userDbStorage.getUserByName("Userfhftgjht").getId();
        userDbStorage.addFriend(userId, friendId);
        Assertions.assertTrue(userDbStorage.getFriends(userId).contains(userDbStorage.getUserById(friendId)),
                "Friend is not added");
    }

    @Test
    public void getFriendsTest() {
        User user = User.builder()
                .email("userrgvre@email.com")
                .name("Userfdsfghgbv")
                .login("loginthdfgx")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1fgvfredf@email.com")
                .name("User1dsfgfnbvf")
                .login("login1dfjh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2ver@email.com")
                .name("User2jhdfgvgf")
                .login("login2gjhfdf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long userId = userDbStorage.getUserByName("Userfdsfghgbv").getId();
        long friend1Id = userDbStorage.getUserByName("User1dsfgfnbvf").getId();
        long friend2Id = userDbStorage.getUserByName("User2jhdfgvgf").getId();
        userDbStorage.addFriend(userId, friend1Id);
        userDbStorage.addFriend(userId, friend2Id);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUserById(friend1Id));
        expectedFriends.add(userDbStorage.getUserById(friend2Id));
        Set<User> actualFriends = userDbStorage.getFriends(userId);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeFriendTest() {
        User user = User.builder()
                .email("userergverd@email.com")
                .name("Userrerlkjgfj")
                .login("loginhtdhgd")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1fvrtd@email.com")
                .name("User1eghtrhgrt")
                .login("login1yjhytg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2ergdfv@email.com")
                .name("User2erukjhdftsee")
                .login("login2tjuyt")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long hasFriendsId = userDbStorage.getUserByName("Userrerlkjgfj").getId();
        long deletedFriendId = userDbStorage.getUserByName("User1eghtrhgrt").getId();
        long savedFriendId = userDbStorage.getUserByName("User2erukjhdftsee").getId();
        userDbStorage.addFriend(hasFriendsId, deletedFriendId);
        userDbStorage.addFriend(hasFriendsId, savedFriendId);
        Set<User> expectedFriends = new HashSet<>();
        expectedFriends.add(userDbStorage.getUserById(savedFriendId));
        userDbStorage.removeFriend(hasFriendsId, deletedFriendId);
        Set<User> actualFriends = userDbStorage.getFriends(hasFriendsId);
        Assertions.assertEquals(expectedFriends, actualFriends,
                "Friends set is not expected");
    }

    @Test
    public void removeAllFriendsTest() {
        User user = User.builder()
                .email("usergfrvedg@email.com")
                .name("Useruyguyhbnmj")
                .login("logingfjhgdf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1ergverdx@email.com")
                .name("User1xcgxfhgkhxc")
                .login("login1dfhb")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dfvcrgv@email.com")
                .name("User2xfjghkgjhgxfj")
                .login("login2jhytfgd")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long hasFriendsId = userDbStorage.getUserByName("Useruyguyhbnmj").getId();
        long deletedFriend1Id = userDbStorage.getUserByName("User1xcgxfhgkhxc").getId();
        long deletedFriend2Id = userDbStorage.getUserByName("User2xfjghkgjhgxfj").getId();
        userDbStorage.addFriend(hasFriendsId, deletedFriend1Id);
        userDbStorage.addFriend(hasFriendsId, deletedFriend2Id);
        userDbStorage.removeAllFriends(hasFriendsId);
        Assertions.assertTrue(userDbStorage.getFriends(hasFriendsId).isEmpty(),
                "Friends set is not empty");
    }

    @Test
    public void getCommonFriendsTest() {
        User user = User.builder()
                .email("useruyjnhb@email.com")
                .name("Userjhgsgszaa")
                .login("loginfhgtrh")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user1 = User.builder()
                .email("user1thgf@email.com")
                .name("User1ghjlkjbv")
                .login("login1jhrdfg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2fghnu@email.com")
                .name("User2kjhgfsgg")
                .login("login2kjhdfg")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long hasFriend1Id = userDbStorage.getUserByName("Userjhgsgszaa").getId();
        long friendId = userDbStorage.getUserByName("User1ghjlkjbv").getId();
        long hasFriend2Id = userDbStorage.getUserByName("User2kjhgfsgg").getId();
        userDbStorage.addFriend(hasFriend1Id, friendId);
        userDbStorage.addFriend(hasFriend2Id, friendId);
        Assertions.assertTrue(userDbStorage.getCommonFriends(hasFriend1Id, hasFriend2Id)
                        .contains(userDbStorage.getUserById(friendId)),
                "Set of common friends is not expected");
    }

    @Test
    public void createFilmTest() {
        Film film = Film.builder()
                .name("filmkjughjhfh")
                .description("filmDescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        long createdFilmId = filmDbStorage.getFilmByName("filmkjughjhfh").getId();
        Assertions.assertNotNull(filmDbStorage.getFilmById(createdFilmId),
                "Film is not created");
    }

    @Test
    public void getFilmByIdTest() {
        Film film = Film.builder()
                .name("filmyijhdghgjh")
                .description("filmDescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(2))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        long getFilmId = filmDbStorage.getFilmByName("filmyijhdghgjh").getId();
        film.setId(getFilmId);
        Assertions.assertEquals(filmDbStorage.getFilmById(getFilmId), film,
                "Actual film is not expected");
    }

    @Test
    public void getFilmsTest() {
        Film film1 = Film.builder()
                .name("film1")
                .description("film1description")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film film2 = Film.builder()
                .name("film2")
                .description("film2description")
                .duration(120)
                .mpa(mpaDbStorage.getMpaById(2))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film1);
        filmDbStorage.createFilm(film2);
        Assertions.assertFalse(filmDbStorage.getFilms().isEmpty(),
                "Films are not found");
    }

    @Test
    public void updateFilmTest() {
        Film film = Film.builder()
                .name("filmaykhjhgxgh")
                .description("filmdescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        Film createdFilm = filmDbStorage.getFilmByName("filmaykhjhgxgh");
        film.setId(createdFilm.getId());
        film.setName("UpdatedFilm");
        filmDbStorage.updateFilm(film);
        Film updatedFilm = filmDbStorage.getFilmById(createdFilm.getId());
        Assertions.assertEquals("UpdatedFilm", updatedFilm.getName(),
                "Actual film is not expected");
    }

    @Test
    public void addLikeTest() {
        Film film = Film.builder()
                .name("filmjhgfffghj")
                .description("description")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        long filmId = filmDbStorage.getFilmByName("filmjhgfffghj").getId();
        User user = User.builder()
                .email("useryfgjhd@email.com")
                .name("Useroiuytrddvbn")
                .login("logindfhbgv")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user);
        long userId = userDbStorage.getUserByName("Useroiuytrddvbn").getId();
        filmDbStorage.addLike(filmId, userId);
        Assertions.assertFalse(filmDbStorage.getLikes(filmId).isEmpty(),
                "Like is not added");
    }

    @Test
    public void getLikesTest() {
        Film film = Film.builder()
                .name("filmdfghjlkjh")
                .description("filmdescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        User user1 = User.builder()
                .email("user1dfgrtgjh@email.com")
                .name("User1sdkjhgscgj")
                .login("login1dxfgj")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dfhgyh@email.com")
                .name("User2fdfgkmnbxc")
                .login("login2fxyhdjk")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long filmId = filmDbStorage.getFilmByName("filmdfghjlkjh").getId();
        long user1Id = userDbStorage.getUserByName("User1sdkjhgscgj").getId();
        long user2Id = userDbStorage.getUserByName("User2fdfgkmnbxc").getId();
        filmDbStorage.addLike(filmId, user1Id);
        filmDbStorage.addLike(filmId, user2Id);
        Set<Long> expectedLikesSet = new HashSet<>(Set.of(user1Id, user2Id));
        Assertions.assertEquals(expectedLikesSet, filmDbStorage.getLikes(filmId),
                "Actual likes set is not expected");
    }

    @Test
    public void removeLikeTest() {
        Film film = Film.builder()
                .name("filmhkgjm")
                .description("filmdescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(film);
        User user1 = User.builder()
                .email("user1dfhntyjg@email.com")
                .name("User1khjhvnm")
                .login("login1hkjf")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user2dhbtfgjhb@email.com")
                .name("User2ccvcmmhb")
                .login("login2hkgmv")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        long filmId = filmDbStorage.getFilmByName("filmhkgjm").getId();
        long user1Id = userDbStorage.getUserByName("User1khjhvnm").getId();
        long user2Id = userDbStorage.getUserByName("User2ccvcmmhb").getId();
        filmDbStorage.addLike(filmId, user1Id);
        filmDbStorage.addLike(filmId, user2Id);
        Set<Long> expectedLikesSet = new HashSet<>(Set.of(user1Id));
        filmDbStorage.removeLike(filmId, user2Id);
        Assertions.assertEquals(expectedLikesSet, filmDbStorage.getLikes(filmId),
                "Actual likes set is not expected");
    }

    @Test
    public void getPopularFilmsTest() {
        Film badFilm = Film.builder()
                .name("filmgggjhjhfb")
                .description("filmdescription")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film goodFilm = Film.builder()
                .name("film1fhjdfdbth")
                .description("film1description")
                .duration(100)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        Film bestFilm = Film.builder()
                .name("film2jhkgvfh")
                .description("film2description")
                .duration(120)
                .mpa(mpaDbStorage.getMpaById(1))
                .releaseDate(LocalDate.of(2025, 4, 5))
                .build();
        filmDbStorage.createFilm(badFilm);
        filmDbStorage.createFilm(goodFilm);
        filmDbStorage.createFilm(bestFilm);
        User user1 = User.builder()
                .email("userfghfcgyg@email.com")
                .name("Userdhjkhjhdxd")
                .login("loginvkhm")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user2 = User.builder()
                .email("user1jkiukhj@email.com")
                .name("User1dfhgjjmngx")
                .login("login1jhkjn")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        User user3 = User.builder()
                .email("user2ukiugfgc@email.com")
                .name("User2xfhjnbvxdgh")
                .login("login2hjij")
                .birthday(LocalDate.of(2024, 5, 5))
                .build();
        userDbStorage.createUser(user1);
        userDbStorage.createUser(user2);
        userDbStorage.createUser(user3);
        long film1Id = filmDbStorage.getFilmByName("filmgggjhjhfb").getId();
        long film2Id = filmDbStorage.getFilmByName("film1fhjdfdbth").getId();
        long film3Id = filmDbStorage.getFilmByName("film2jhkgvfh").getId();
        long user1Id = userDbStorage.getUserByName("Userdhjkhjhdxd").getId();
        long user2Id = userDbStorage.getUserByName("User1dfhgjjmngx").getId();
        long user3Id = userDbStorage.getUserByName("User2xfhjnbvxdgh").getId();
        filmDbStorage.addLike(film2Id, user1Id);
        filmDbStorage.addLike(film2Id, user2Id);
        filmDbStorage.addLike(film3Id, user1Id);
        filmDbStorage.addLike(film3Id, user2Id);
        filmDbStorage.addLike(film3Id, user3Id);
        List<Film> expectedTopList = new ArrayList<>(List.of(bestFilm, goodFilm));
        Assertions.assertEquals(expectedTopList, filmDbStorage.getPopularFilms(2),
                "Actual toplist is not expected");
    }

}