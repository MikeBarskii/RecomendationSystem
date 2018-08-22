package ru.barskii.entry;

import java.util.ArrayList;
import java.util.List;

public class Rater {
    private long myID;
    private ArrayList<Rating> myRatings;

    public Rater(long id) {
        myID = id;
        myRatings = new ArrayList<>();
    }

    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item, rating));
    }

    public boolean hasRating(String item) {
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(item)) {
                return true;
            }
        }

        return false;
    }

    public long getID() {
        return myID;
    }

    public double getRating(String item) {
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(item)) {
                return myRating.getValue();
            }
        }

        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public List<String> getItemsRated() {
        List<String> list = new ArrayList<>();
        for (Rating myRating : myRatings) {
            list.add(myRating.getItem());
        }

        return list;
    }
}
