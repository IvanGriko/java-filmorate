CREATE TABLE "users"(
    "userId" FLOAT(53) NOT NULL,
    "name" TEXT NOT NULL,
    "login" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "birthday" DATE NOT NULL
);
ALTER TABLE
    "users" ADD PRIMARY KEY("userId");
CREATE TABLE "films"(
    "filmId" FLOAT(53) NOT NULL,
    "name" TEXT NOT NULL,
    "description" CHAR(200) NOT NULL,
    "releaseDate" DATE NOT NULL,
    "duration" INTEGER NOT NULL,
    "mpaId" INTEGER NOT NULL
);
ALTER TABLE
    "films" ADD PRIMARY KEY("filmId");
CREATE TABLE "genres"(
    "genreId" INTEGER NOT NULL,
    "name" TEXT NOT NULL
);
ALTER TABLE
    "genres" ADD PRIMARY KEY("genreId");
CREATE TABLE "mpa_ratings"(
    "mpaId" INTEGER NOT NULL,
    "name" TEXT NOT NULL
);
ALTER TABLE
    "mpa_ratings" ADD PRIMARY KEY("mpaId");
CREATE TABLE "likes"(
    "filmId" FLOAT(53) NOT NULL,
    "userId" FLOAT(53) NOT NULL
);
ALTER TABLE
    "likes" ADD PRIMARY KEY("filmId");
CREATE TABLE "friendship"(
    "userId" FLOAT(53) NOT NULL,
    "friendId" FLOAT(53) NOT NULL,
    "friendship verification" BOOLEAN NOT NULL
);
ALTER TABLE
    "friendship" ADD PRIMARY KEY("userId");
ALTER TABLE
    "friendship" ADD PRIMARY KEY("friendId");
CREATE TABLE "film_genres"(
    "filmId" FLOAT(53) NOT NULL,
    "genreId" INTEGER NOT NULL
);
ALTER TABLE
    "film_genres" ADD PRIMARY KEY("filmId");
ALTER TABLE
    "films" ADD CONSTRAINT "films_mpaid_foreign" FOREIGN KEY("mpaId") REFERENCES "mpa_ratings"("mpaId");
ALTER TABLE
    "users" ADD CONSTRAINT "users_userid_foreign" FOREIGN KEY("userId") REFERENCES "friendship"("friendId");
ALTER TABLE
    "film_genres" ADD CONSTRAINT "film_genres_filmid_foreign" FOREIGN KEY("filmId") REFERENCES "films"("filmId");
ALTER TABLE
    "likes" ADD CONSTRAINT "likes_filmid_foreign" FOREIGN KEY("filmId") REFERENCES "films"("filmId");
ALTER TABLE
    "film_genres" ADD CONSTRAINT "film_genres_genreid_foreign" FOREIGN KEY("genreId") REFERENCES "genres"("genreId");
ALTER TABLE
    "likes" ADD CONSTRAINT "likes_userid_foreign" FOREIGN KEY("userId") REFERENCES "users"("userId");
ALTER TABLE
    "users" ADD CONSTRAINT "users_userid_foreign" FOREIGN KEY("userId") REFERENCES "friendship"("userId");