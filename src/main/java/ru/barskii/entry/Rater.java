package ru.barskii.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rater {
    private long myID;
    private ArrayList<Rating> myRatings;

    public Rater(long id) {
        myID = id;
        myRatings = new ArrayList<>();
    }

    public void addRating(long movie, double rating) {
        myRatings.add(new Rating(movie, rating));
    }

    public boolean hasRating(long movieId) {
        return myRatings.stream()
                .map(Rating::getMovie)
                .anyMatch(movie -> movie == movieId);
    }

    public long getID() {
        return myID;
    }

    public double getRating(long movieId) {
        for (Rating myRating : myRatings) {
            if (myRating.getMovie() == movieId) {
                return myRating.getRating();
            }
        }

        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public List<Long> getItemsRated() {
        return myRatings.stream()
                .map(Rating::getMovie)
                .collect(Collectors.toList());
    }
}
