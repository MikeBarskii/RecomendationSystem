package ru.barskii.filters;

public class TrueFilter implements Filter {
    @Override
    public boolean satisfies(long id) {
        return true;
    }

}
