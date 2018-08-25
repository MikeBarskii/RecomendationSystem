package ru.barskii.filter;

import java.util.ArrayList;
import java.util.List;

public class AllFilters implements Filter {
    private List<Filter> filters;

    public AllFilters() {
        filters = new ArrayList<>();
    }

    public AllFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(long id) {
        return filters.stream()
                .allMatch(filter -> filter.satisfies(id));
    }
}
