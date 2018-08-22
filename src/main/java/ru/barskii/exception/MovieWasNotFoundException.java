package ru.barskii.exception;

public class MovieWasNotFoundException extends RuntimeException {
    public MovieWasNotFoundException(long movieId) {
        super("Movie was not found with id: " + movieId);
    }

    public MovieWasNotFoundException(String movieTitle) {
        super("Movie was not found with title: " + movieTitle);
    }
}
