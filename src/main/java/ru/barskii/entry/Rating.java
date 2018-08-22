package ru.barskii.entry;

public class Rating implements Comparable<Rating> {
    private long movie;
    private double rating;

    public Rating(long movieId, double rating) {
        movie = movieId;
        this.rating = rating;
    }

    public long getMovie() {
        return movie;
    }

    public double getRating() {
        return rating;
    }

    public String toString() {
        return "[" + getMovie() + ", " + getRating() + "]";
    }

    public int compareTo(Rating other) {
        return Double.compare(rating, other.rating);
    }
}
