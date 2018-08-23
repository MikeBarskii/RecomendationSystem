package ru.barskii;

import ru.barskii.entry.Rating;
import ru.barskii.ratings.SecondRatings;

import java.text.DecimalFormat;
import java.util.Comparator;

public class MovieRunnerAverage {
    private static DecimalFormat df2 = new DecimalFormat(".####");

    public void printAverageRatings(String movieFilename, String ratersFilename, int minRaters) {
        SecondRatings secondRatings = new SecondRatings(movieFilename, ratersFilename);
        secondRatings.getAverageRatings(minRaters).stream()
                .sorted(Comparator.comparingDouble(Rating::getRating))
                .forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " + secondRatings.getTitle(rating.getMovie())));
    }

    public void getAverageRatingOneMovie(String movieFilename, String ratersFilename, String movieTitle, int minRaters) {
        SecondRatings secondRatings = new SecondRatings(movieFilename, ratersFilename);
        long movieId = secondRatings.getID(movieTitle);
        double averageRatingForMovie = secondRatings.getAverageRatingByMovieIDWithMinRaters(movieId, 3);
        System.out.println("Average rating for movie \"" + movieTitle + "\": " + averageRatingForMovie);
    }
}
