package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.Coordinates;

public class CoordinateSearchResult {

    private Coordinates firstSearched, secondSearched;

    public boolean isFirstFound() {
        return firstSearched != null;
    }

    public boolean isSecondFound() {
        return secondSearched != null;
    }

    public Coordinates getFirstSearched() {
        return firstSearched;
    }

    public void setFirstSearched(Coordinates firstSearched) {
        this.firstSearched = firstSearched;
    }

    public Coordinates getSecondSearched() {
        return secondSearched;
    }

    public void setSecondSearched(Coordinates secondSearched) {
        this.secondSearched = secondSearched;
    }
}