package ru.barskii.db;

import ru.barskii.entry.Movie;
import ru.barskii.filters.Filter;
import ru.barskii.ratings.FirstRatings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MovieDatabase {
    private static HashMap<Long, Movie> ourMovies;

    public static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<Long, Movie>();
            loadMovies("data/" + moviefile);
        }
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<Long, Movie>();
            loadMovies("data/ratedmoviesfull.csv");
        }
    }

    private static void loadMovies(String filename) {
        FirstRatings fr = new FirstRatings();
        Set<Movie> list = fr.loadMovies(filename);
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

    public static boolean containsID(long id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(long id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(long id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(long id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(long id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(long id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(long id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(long id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(long id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static List<Long> filterBy(Filter f) {
        initialize();
        List<Long> list = new ArrayList<>();
        for (long id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }

        return list;
    }

}
