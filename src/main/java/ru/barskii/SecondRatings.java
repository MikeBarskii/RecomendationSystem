package ru.barskii;

import ru.barskii.entry.Movie;
import ru.barskii.entry.Rater;
import ru.barskii.entry.Rating;
import ru.barskii.exception.MovieWasNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SecondRatings {
    private Set<Movie> movies;
    private Set<Rater> raters;

    public SecondRatings(String moviesFile, String ratingsFile) {
        FirstRatings firstRatings = new FirstRatings();
        movies = firstRatings.loadMovies(moviesFile);
        raters = firstRatings.loadRaters(ratingsFile);
    }

    public int getNumberOfMovies() {
        return movies.size();
    }

    public int getNumberOfRaters() {
        return raters.size();
    }

    public String getTitle(long movieId) {
        return movies.stream()
                .filter(movie -> movie.getID() == movieId)
                .map(Movie::getTitle)
                .findFirst()
                .orElseThrow(() -> new MovieWasNotFoundException(movieId));
    }

    public long getID(String movieTitle) {
        return movies.stream()
                .filter(movie -> movieTitle.equals(movie.getTitle()))
                .map(Movie::getID)
                .findFirst()
                .orElseThrow(() -> new MovieWasNotFoundException(movieTitle));
    }

    public List<Rating> getAverageRatings(int minRaters) {
        List<Rating> ratings = new ArrayList<>();
        for (Movie movie : movies) {
            double averageRating = getAverageRatingByMovieIDWithMinRaters(movie.getID(), minRaters);
            if (averageRating != 0)
                ratings.add(new Rating(movie.getID(), averageRating));
        }
        return ratings;
    }

    public double getAverageRatingByMovieIDWithMinRaters(long movieId, int minRaters) {
        FirstRatings firstRatings = new FirstRatings();
        List<Double> ratings = firstRatings.getRatingsForMovie(raters, movieId);
        if (ratings.size() >= minRaters)
            return ratings.stream().mapToDouble(i -> i).sum() / ratings.size();
        return 0.0;
    }
}