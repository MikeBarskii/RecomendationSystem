package ru.barskii;

import ru.barskii.db.MovieDatabase;

public class Main {

    public static void main(String[] args) {
        MovieRunnerWithFilters movieRunner = new MovieRunnerWithFilters();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        movieRunner.printAverageRatings("ratings.csv", 4);
    }
}
