package ru.barskii;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.barskii.entry.Movie;
import ru.barskii.entry.Rater;

import java.util.*;
import java.util.stream.Collectors;

public class FirstRatings {

    public void testGetMaxWorkingRaters(String filename) {
        Set<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());

        Set<Rater> popularRaters = getRatersWithMaxRatings(raters);
        System.out.println("Raters with maximum ratings: " + popularRaters);
    }

    public void testGetMoviesByRaters(String filename) {
        Set<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());

        Set<Long> movies = collectMoviesIdFromRaters(raters);
        System.out.println("Movie Ids with ratings: " + movies);
    }

    public void testGetRatingsByMovieId(String filename, long movieId) {
        Set<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());

        List<Double> ratings = getRatingsForMovie(raters, movieId);
        System.out.println("Ratings for film " + movieId + ": " + ratings);
    }

    public void testCollectDirectorAndTheirFilms(String filename) {
        Set<Movie> movies = loadMovies(filename);
        System.out.println("Total number of movies: " + movies.size());

        Map<String, Set<String>> directorAndTheirFilms = collectDirectorAndTheirFilms(movies);
        System.out.println(directorAndTheirFilms.toString());

        int maxMoviesByOneDirector = getMaxMoviesByOneDirector(directorAndTheirFilms);
        System.out.println("Max movies by one director: " + maxMoviesByOneDirector);

        Set<String> nameOfDirectorsWithMaxMoviesQuantity = getNameOfDirectorsWithMoviesQuantity(directorAndTheirFilms, maxMoviesByOneDirector);
        System.out.println(nameOfDirectorsWithMaxMoviesQuantity.toString());
    }

    public void testSortMoviesByGenre(String filename, String genre) {
        Set<Movie> movies = loadMovies(filename);
        System.out.println("Total number of movies: " + movies.size());

        Set<Movie> moviesAfterSort = sortMoviesByGenre(movies, genre);
        System.out.println("Total number of movies by genre " + genre + ": " + moviesAfterSort.size());
    }

    public void testSortMoviesByLongerRuntime(String filename, int length) {
        Set<Movie> movies = loadMovies(filename);
        System.out.println("Total number of movies: " + movies.size());

        Set<Movie> moviesAfterSort = sortMoviesByLongerRuntime(movies, length);
        System.out.println("Total number of movies longer than " + length + " minutes: " + moviesAfterSort.size());
    }

    public void testGetRaterById(String filename, long raterId) {
        Set<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());

        Rater rater = getRaterById(raters, raterId);
        System.out.println("Rater with id " + raterId + ": " + rater);
    }

    public void testLoadMovies(String filename) {
        Set<Movie> movies = loadMovies(filename);
        System.out.println("Total number of movies: " + movies.size());
    }

    public void testLoadRaters(String filename) {
        Set<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());
    }

    public Set<Movie> loadMovies(String filename) {
        CSVParser csvParser = getCsvParser(filename);

        Set<Movie> movies = new HashSet<>();
        for (CSVRecord record : csvParser) {
            Movie movie = new Movie(Long.parseLong(record.get("id")), record.get("title"), record.get("year"), record.get("genre"), record.get("director"),
                    record.get("country"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            movies.add(movie);
        }
        return movies;
    }

    public Set<Rater> loadRaters(String filename) {
        CSVParser csvParser = getCsvParser(filename);

        Set<Rater> raters = new HashSet<>();
        for (CSVRecord record : csvParser) {
            long raterId = Long.parseLong(record.get("rater_id"));
            Rater rater = getRaterById(raters, raterId);
            if (rater == null) {
                rater = new Rater(raterId);
                raters.add(rater);
            }

            rater.addRating(Long.parseLong(record.get("movie_id")), Double.parseDouble(record.get("rating")));
        }
        return raters;
    }

    private Set<Movie> sortMoviesByGenre(Set<Movie> movies, String genre) {
        return movies.stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toSet());
    }

    private Set<Movie> sortMoviesByLongerRuntime(Set<Movie> movies, int length) {
        return movies.stream()
                .filter(movie -> movie.getMinutes() > length)
                .collect(Collectors.toSet());
    }

    private Set<Rater> getRatersWithMaxRatings(Set<Rater> raters) {
        int maxQuantityOfRatings = getMaxQuantityOfRatings(raters);
        return raters.stream()
                .filter(rater -> rater.getItemsRated().size() == maxQuantityOfRatings)
                .collect(Collectors.toSet());
    }

    public List<Double> getRatingsForMovie(Set<Rater> raters, long movieId) {
        return raters.stream()
                .filter(rater -> rater.getRating(movieId) != -1)
                .map(rater -> rater.getRating(movieId))
                .collect(Collectors.toList());
    }

    private Set<Long> collectMoviesIdFromRaters(Set<Rater> raters) {
        Set<Long> movies = new HashSet<>();
        for (Rater rater : raters) {
            List<Long> ratedMovies = rater.getItemsRated();
            movies.addAll(ratedMovies);
        }
        return movies;
    }

    private Map<String, Set<String>> collectDirectorAndTheirFilms(Set<Movie> movies) {
        Map<String, Set<String>> directorAndFilms = new HashMap<>();
        for (Movie movie : movies) {
            String[] directors = movie.getDirector().split(",");
            for (String director : directors) {
                addDirectorIntoMapWithFilms(directorAndFilms, director, movie.getTitle());
            }
        }
        return directorAndFilms;
    }

    private int getMaxMoviesByOneDirector(Map<String, Set<String>> directorAndMovies) {
        return directorAndMovies.values().stream()
                .max(Comparator.comparingInt(Set::size))
                .map(Set::size)
                .orElse(0);
    }

    private Set<String> getNameOfDirectorsWithMoviesQuantity(Map<String, Set<String>> directorAndMovies, int moviesQuantity) {
        return directorAndMovies.entrySet().stream()
                .filter(movies -> movies.getValue().size() == moviesQuantity)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private Rater getRaterById(Set<Rater> raters, long id) {
        return raters.stream()
                .filter(rater -> rater.getID() == id)
                .findFirst()
                .orElse(null);
    }

    private int getMaxQuantityOfRatings(Set<Rater> raters) {
        int maxQuantityRatings = 0;
        for (Rater rater : raters) {
            int quantityOfRatings = rater.getItemsRated().size();
            if (quantityOfRatings > maxQuantityRatings)
                maxQuantityRatings = quantityOfRatings;
        }
        return maxQuantityRatings;
    }

    private void addDirectorIntoMapWithFilms(Map<String, Set<String>> directorAndFilms, String director, String movieTitle) {
        if (directorAndFilms.containsKey(director))
            directorAndFilms.get(director).add(movieTitle);
        else directorAndFilms.put(director, new HashSet<>(Collections.singletonList(movieTitle)));
    }

    private CSVParser getCsvParser(String filename) {
        FileResource fileResource = new FileResource(filename);
        return fileResource.getCSVParser(true);
    }
}
