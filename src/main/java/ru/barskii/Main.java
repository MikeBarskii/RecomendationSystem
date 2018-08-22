package ru.barskii;

public class Main {

    public static void main(String[] args) {
        MovieRunnerAverage movieRunner = new MovieRunnerAverage();
        movieRunner.printAverageRatings("ratedmoviesfull.csv", "ratings.csv", 50);
    }
}
