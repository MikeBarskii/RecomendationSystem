package ru.barskii.entry;

import java.util.List;

public interface Rater {

    void addRating(long movie, double rating);

    boolean hasRating(long movieId);

    long getID();

    double getRating(long movieId);

    int numRatings();

    List<Long> getItemsRated();
}
