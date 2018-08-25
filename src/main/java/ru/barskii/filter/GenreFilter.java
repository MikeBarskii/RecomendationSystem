package ru.barskii.filter;

import ru.barskii.db.MovieDatabase;

public class GenreFilter implements Filter {
    private String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean satisfies(long id) {
        return MovieDatabase.getGenres(id).contains(genre);
    }

}
