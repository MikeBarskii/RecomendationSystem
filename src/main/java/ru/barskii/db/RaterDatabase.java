package ru.barskii.db;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.barskii.entry.EfficientRater;
import ru.barskii.entry.Rater;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RaterDatabase {
    private static Map<Long, Rater> raters;

    private static void initialize() {
        // this method is only called from addRatings
        if (raters == null)
            raters = new HashMap<>();
    }

    public static void initialize(String filename) {
        if (raters == null) {
            raters = new HashMap<>();
            addRatings(filename);
        }
    }

    public static void addRatings(String filename) {
        initialize();
        FileResource fr = new FileResource(filename);
        CSVParser csvp = fr.getCSVParser();
        for (CSVRecord rec : csvp) {
            long raterId = Long.parseLong(rec.get("rater_id"));
            long movieId = Long.parseLong(rec.get("movie_id"));
            String rating = rec.get("rating");
            addRaterRating(raterId, movieId, Double.parseDouble(rating));
        }
    }

    public static void addRaterRating(long raterID, long movieID, double rating) {
        initialize();
        Rater rater;
        if (raters.containsKey(raterID)) {
            rater = raters.get(raterID);
        } else {
            rater = new EfficientRater(raterID);
            raters.put(raterID, rater);
        }
        rater.addRating(movieID, rating);
    }

    public static Rater getRater(long raterId) {
        initialize();
        return raters.get(raterId);
    }

    public static Set<Rater> getRaters() {
        initialize();
        return (Set<Rater>) raters.values();
    }

    public static int size() {
        return raters.size();
    }


}