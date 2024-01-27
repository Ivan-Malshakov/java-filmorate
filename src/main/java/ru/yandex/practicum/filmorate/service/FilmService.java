package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenresStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService {
    FilmStorage storage;
    FilmGenresStorage filmGenresStorage;
    LikeStorage likeStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage storage,
                       FilmGenresStorage filmGenresStorage,
                       LikeStorage likeStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage) {
        this.storage = storage;
        this.filmGenresStorage = filmGenresStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.getData(userId);
        likeStorage.addLike(filmId, userId);

    }

    public void deleteLike(Long filmId, Long userId) {
        userStorage.getData(userId);
        likeStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopular(Integer count) {
        return storage.getAll().stream()
                .sorted(filmComparator.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private final Comparator<Film> filmComparator = new Comparator<Film>() {
        @Override
        public int compare(Film o1, Film o2) {
            if (o1.getRate() == o2.getRate()) {
                return o1.getId().compareTo(o2.getId());
            } else if (o1.getRate() > o2.getRate()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public Film create(Film data) {
        Film film = storage.create(data);
        if (data.getGenres().size() != 0) {
            filmGenresStorage.updateGenresForFilm(film, data.getGenres());
            film.setGenres(filmGenresStorage.getGenresForFilm(film.getId()));
        }
        return film;
    }

    public Film update(Film data) {
        Film film = storage.update(data);

        Set<Genre> genres = data.getGenres();
        int genresSetSize = genres.size();

        filmGenresStorage.updateGenresForFilm(film, genres);
        if (genresSetSize != 0) {
            filmGenresStorage.updateGenresForFilm(film, data.getGenres());
            film.setGenres(filmGenresStorage.getGenresForFilm(film.getId()));
        } else if (genresSetSize == 0) {
            film.setGenres(new LinkedHashSet<>());
        }
        return film;
    }

    public List<Film> getAll() {
        List<Film> films = storage.getAll();
        List<Film> filmsWithGenres = new ArrayList<>();
        for (Film film : films) {
            Long id = film.getId();
            film.setGenres(filmGenresStorage.getGenresForFilm(id));
            filmsWithGenres.add(film);
        }
        return filmsWithGenres;
    }

    public Film getData(Long id) {
        Film film = storage.getData(id);
        film.setGenres(filmGenresStorage.getGenresForFilm(id));
        return film;
    }

}
