package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cells;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SearchResults {

    private Set<TableCoordinates> coordSet;
    private TableCoordinates initCoordsToCheck;
    private List<CoordinateSearchResult> coordSearchResults;

    private Cells cells;

    public SearchResults(TableCoordinates initCoordsToCheck,
                         ControlFactorType[] factorTypes,
                         TokenColor controlColor,
                         Cells cells) {
        this.coordSet = new HashSet<>() {{ add(initCoordsToCheck); }};

        this.coordSearchResults = new ArrayList<>(factorTypes.length) {{
            IntStream.range(0, factorTypes.length)
                    .forEach(i -> this.add(new CoordinateSearchResult(factorTypes[i], controlColor)));
        }};

        this.cells = cells;
        this.initCoordsToCheck = initCoordsToCheck;
    }

    public List<CoordinateSearchResult> getCoordSearchResults() {
        return coordSearchResults;
    }

    public Set<TableCoordinates> getCoordSet() {
        return coordSet;
    }

    public boolean areControlsAvailable() {
        return coordSearchResults.stream()
                .map(CoordinateSearchResult::isControlAvailable)
                .reduce((first, second) -> first || second).get();
    }

    public void controlCoordinateCouple(int firstResultToCheck, int cycle) {
        int secondResultToCheck = firstResultToCheck + 1;

        this.coordSearchResults.get(firstResultToCheck).control(cells, initCoordsToCheck, cycle);
        this.coordSearchResults.get(secondResultToCheck).control(cells, initCoordsToCheck, cycle);

        int firstCoordSearchSize = getSizeOfCoordinateSearchResult(firstResultToCheck);
        int secondCoordSearchSize = getSizeOfCoordinateSearchResult(secondResultToCheck);
        int coupleSize = Math.addExact(firstCoordSearchSize, secondCoordSearchSize);

        if (coupleSize == 3) {
            coordSet.addAll(coordSearchResults.get(firstResultToCheck).getSearchResult());
            coordSet.addAll(coordSearchResults.get(secondResultToCheck).getSearchResult());
        }

        else if (coupleSize >= 4) {
            if (resultNotEmpty(firstResultToCheck))
                coordSet.add(coordSearchResults.get(firstResultToCheck).getSearchResultCoords( firstCoordSearchSize - 1));
            if (resultNotEmpty(secondResultToCheck))
                coordSet.add(coordSearchResults.get(secondResultToCheck).getSearchResultCoords( secondCoordSearchSize - 1));
        }
    }

    private boolean resultNotEmpty(int resultToCheck) {
        return !coordSearchResults.get(resultToCheck).getSearchResult().isEmpty();
    }

    private int getSizeOfCoordinateSearchResult(int firstResultToCheck) {
        return this.coordSearchResults.get(firstResultToCheck).getSearchResult().size();
    }

}