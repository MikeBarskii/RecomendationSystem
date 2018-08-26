package ru.barskii.rating;

import ru.barskii.db.MovieDatabase;
import ru.barskii.db.RaterDatabase;
import ru.barskii.entry.Rater;
import ru.barskii.entry.Rating;
import ru.barskii.filter.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ThirdRatings {
    private static DecimalFormat df2 = new DecimalFormat(".####");

    private Set<Rater> raters;
    private int minRaters = 1;

    public ThirdRatings(String ratingsFile) {
        FirstRatings firstRatings = new FirstRatings();
        raters = firstRatings.loadRaters(ratingsFile);
    }

    public int getNumberOfRaters() {
        return raters.size();
    }

    public void printAverageRatingsByYear(int afterYear) {
        Filter filter = new YearAfterFilter(afterYear);

        List<Rating> ratings = getAverageRatings(filter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getYear(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())));
    }

    public void printAverageRatingsByGenre(String genre) {
        Filter filter = new GenreFilter(genre);

        List<Rating> ratings = getAverageRatings(filter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getTitle(rating.getMovie()) + ", " + MovieDatabase.getGenres(rating.getMovie())));
    }

    public void printAverageRatingsByMinutes(int minMinutes, int maxMinutes) {
        Filter filter = new MinutesFilter(minMinutes, maxMinutes);

        List<Rating> ratings = getAverageRatings(filter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " Time: " +
                MovieDatabase.getMinutes(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())));
    }

    public void printAverageRatingsByDirectors(String directors) {
        Filter filter = new DirectorsFilter(directors);

        List<Rating> ratings = getAverageRatings(filter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getTitle(rating.getMovie()) + " " + MovieDatabase.getDirector(rating.getMovie())));
    }

    public void printAverageRatingsByYearAfterAndGenre(int afterYear, String genre) {
        Filter yearFilter = new YearAfterFilter(afterYear);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters allFilter = new AllFilters(Arrays.asList(yearFilter, genreFilter));

        List<Rating> ratings = getAverageRatings(allFilter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getYear(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())
                + "\n \t" + MovieDatabase.getGenres(rating.getMovie())));
    }

    public void printAverageRatingsByDirectorsAndMinutes(int minMinutes, int maxMinutes, String directors) {
        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        Filter directorsFilter = new DirectorsFilter(directors);
        AllFilters allFilter = new AllFilters(Arrays.asList(minutesFilter, directorsFilter));

        List<Rating> ratings = getAverageRatings(allFilter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " Time: " +
                MovieDatabase.getMinutes(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())
                + "\n\t" + MovieDatabase.getDirector(rating.getMovie())));
    }

    public int getMinRaters() {
        return minRaters;
    }

    public void setMinRaters(int minRaters) {
        this.minRaters = minRaters;
    }

    private List<Rating> getAverageRatings(Filter filter) {
        List<Rating> ratings = new ArrayList<>();

        Set<Long> movies = MovieDatabase.filterBy(filter);
        for (Long movieId : movies) {
            List<Double> ratingsForMovie = getRatingsForMovie(movieId);
            if (ratingsForMovie.size() < minRaters)
                continue;

            double averageRating = getAverageValueFromList(ratingsForMovie);
            ratings.add(new Rating(movieId, averageRating));
        }
        Collections.sort(ratings);
        return ratings;
    }

    private List<Double> getRatingsForMovie(long movieId) {
        return new FirstRatings().getRatingsForMovie(RaterDatabase.getRaters(), movieId);
    }

    private double getAverageValueFromList(List<Double> numbers) {
        return numbers.stream().collect(Collectors.averagingDouble(i -> i));
    }

}