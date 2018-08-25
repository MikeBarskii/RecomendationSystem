package ru.barskii.filter;

import ru.barskii.db.MovieDatabase;

import java.util.Arrays;
import java.util.List;

public class DirectorsFilter implements Filter {
    private static final String DELIMITER_FOR_INPUT_DIRECTORS = ",";
    private static final String DELIMITER_FOR_CSV_DIRECTORS = ", ";
    private String[] directors;

    public DirectorsFilter(String directors) {
        this.directors = directors.split(DELIMITER_FOR_INPUT_DIRECTORS);
    }

    @Override
    public boolean satisfies(long id) {
        List<String> directorsForMovie = Arrays.asList(MovieDatabase.getDirector(id).split(DELIMITER_FOR_CSV_DIRECTORS));
        for (String director : directors) {
            if (directorsForMovie.contains(director))
                return true;
        }
        return false;
    }
}
