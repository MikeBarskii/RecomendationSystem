package ru.barskii.rating;

import ru.barskii.db.MovieDatabase;
import ru.barskii.db.RaterDatabase;
import ru.barskii.entry.Rater;
import ru.barskii.entry.Rating;
import ru.barskii.filter.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FourthRatings {

    private int minRaters = 1;

    public int getMinRaters() {
        return minRaters;
    }

    public void setMinRaters(int minRaters) {
        this.minRaters = minRaters;
    }

    public List<Rating> getAverageRatings(Filter filter) {
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

    private double dotProduct(Rater current, Rater other) {
        double affinity = 0;
        List<Long> ratedByCurrentUser = current.getItemsRated();
        for (Long movie : ratedByCurrentUser) {
            if (other.hasRating(movie))
                affinity += (current.getRating(movie) - 5) * (other.getRating(movie) - 5);
        }
        return affinity;
    }
}
