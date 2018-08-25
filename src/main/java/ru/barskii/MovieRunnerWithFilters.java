package ru.barskii;

import ru.barskii.rating.ThirdRatings;

public class MovieRunnerWithFilters {

    public void printAverageRatings(String ratersFilename, int minRaters) {
        ThirdRatings ratings = new ThirdRatings(ratersFilename);
        ratings.setMinRaters(minRaters);
        System.out.println("Number of raters: " + ratings.getNumberOfRaters());

       ratings.printAverageRatingsByDirectors("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
    }
}
