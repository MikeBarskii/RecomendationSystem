package ru.barskii.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EfficientRater implements Rater {
    private long raterId;
    private Map<Long, Rating> ratings;

    public EfficientRater(long id) {
        raterId = id;
        ratings = new HashMap<>();
    }

    public void addRating(long movie, double rating) {
        ratings.put(movie, new Rating(movie, rating));
    }

    public boolean hasRating(long movieId) {
        return ratings.containsKey(movieId);
    }

    @Override
    public long getID() {
        return raterId;
    }

    public double getRating(long movieId) {
        return ratings.get(movieId).getRating();
    }

    public int numRatings() {
        return ratings.size();
    }

    public List<Long> getItemsRated() {
        return new ArrayList<>(ratings.keySet());
    }
}
