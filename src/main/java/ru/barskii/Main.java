package ru.barskii;

public class Main {

    public static void main(String[] args) {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.testGetRatingsByMovieId( "ratings.csv", "1798709");
    }
}
