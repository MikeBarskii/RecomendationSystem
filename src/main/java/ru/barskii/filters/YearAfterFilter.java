package ru.barskii.filters;

import ru.barskii.db.MovieDatabase;

public class YearAfterFilter implements Filter {
    private int myYear;

    public YearAfterFilter(int year) {
        myYear = year;
    }

    @Override
    public boolean satisfies(long id) {
        return MovieDatabase.getYear(id) >= myYear;
    }

}
