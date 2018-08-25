package ru.barskii.filter;

import ru.barskii.db.MovieDatabase;

public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes) {
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }

    @Override
    public boolean satisfies(long id) {
        int durationOfFilm = MovieDatabase.getMinutes(id);
        return durationOfFilm >= minMinutes && durationOfFilm <= maxMinutes;
    }

}