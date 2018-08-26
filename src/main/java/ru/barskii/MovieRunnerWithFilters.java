package ru.barskii;

import ru.barskii.db.MovieDatabase;
import ru.barskii.entry.Rating;
import ru.barskii.filter.AllFilters;
import ru.barskii.filter.Filter;
import ru.barskii.filter.GenreFilter;
import ru.barskii.filter.YearAfterFilter;
import ru.barskii.rating.FourthRatings;
import ru.barskii.rating.ThirdRatings;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MovieRunnerWithFilters {
    private static DecimalFormat df2 = new DecimalFormat(".####");

    public void printAverageRatings(String ratersFilename, int minRaters) {
        ThirdRatings ratings = new ThirdRatings(ratersFilename);
        ratings.setMinRaters(minRaters);
        System.out.println("Number of raters: " + ratings.getNumberOfRaters());

       ratings.printAverageRatingsByDirectors("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
    }

    public void printAverageRatingsByYear(int afterYear) {
        Filter filter = new YearAfterFilter(afterYear);

        FourthRatings fourthRatings = new FourthRatings();
        List<Rating> ratings = fourthRatings.getAverageRatings(filter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getYear(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())));
    }

    public void printAverageRatingsByYearAfterAndGenre(int afterYear, String genre) {
        Filter yearFilter = new YearAfterFilter(afterYear);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters allFilter = new AllFilters(Arrays.asList(yearFilter, genreFilter));

        FourthRatings fourthRatings = new FourthRatings();
        List<Rating> ratings = fourthRatings.getAverageRatings(allFilter);
        System.out.println("Found " + ratings.size() + " movies");

        ratings.forEach(rating -> System.out.println(df2.format(rating.getRating()) + " " +
                MovieDatabase.getYear(rating.getMovie()) + " " + MovieDatabase.getTitle(rating.getMovie())
                + "\n \t" + MovieDatabase.getGenres(rating.getMovie())));
    }
}
