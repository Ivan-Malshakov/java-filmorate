package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    FilmStorage storage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    public void addLike(Long filmId, Long userId) {
        storage.getData(filmId).putLike(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = storage.getData(filmId);
        if (!film.getLikes().contains(userId)) {
            throw new DataNotFoundException(String.format("User id = '%s' not found.", userId));
        }
        film.removeLike(userId);
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
            if (o1.getLikes().size() == o2.getLikes().size()) {
                return o1.getId().compareTo(o2.getId());
            } else if (o1.getLikes().size() > o2.getLikes().size()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public Film create(Film data) {
        storage.create(data);
        return data;
    }

    public Film update(Film data) {
        storage.update(data);
        return data;
    }

    public List<Film> getAll() {
        return storage.getAll();
    }

    public Film getData(Long id) {
        return storage.getData(id);
    }

}
