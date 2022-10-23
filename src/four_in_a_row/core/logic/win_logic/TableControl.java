package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Cells;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class TableControl {

    private Cells cells;
    private TokenColor controlColor;

    public TableControl(Cells cells, TokenColor controlColor) {
        this.cells = cells;
        this.controlColor = controlColor;
    }

    public Set<TableCoordinates> controlFourInARow(TableCoordinates initCoordsToCheck, ControlFactorType... controlFactors) {
        Set<TableCoordinates> coordSet = new HashSet<>() {{ add(initCoordsToCheck); }};

        List<CoordinateSearchResult> searchResults = new ArrayList<>() {{
            IntStream.range(0, controlFactors.length)
                    .forEach(i -> this.add(new CoordinateSearchResult(controlFactors[i], controlColor)));
        }};

        IntStream.iterate(1, i -> searchResults.stream()
                        .map(CoordinateSearchResult::isControlAvailable)
                        .reduce((first, second) -> first || second).get(), i -> i + 1)

                .forEach(i ->
                        IntStream.iterate(0, j -> j < searchResults.size() - 1, j -> j + 2)
                                .forEach(j -> {
                                    // you need to iterate two items each time in order to
                                    // have the possibility to join the result per search
                                    searchResults.get(j).control(cells, initCoordsToCheck, i);
                                    searchResults.get(j + 1).control(cells, initCoordsToCheck, i);

                                    int searchCoordsSize = searchResults.get(j).getSearchResult().size();
                                    int searchCoordsSize1 = searchResults.get(j + 1).getSearchResult().size();

                                    if (searchCoordsSize + searchCoordsSize1 == 3) {
                                        coordSet.addAll(searchResults.get(j).getSearchResult());
                                        coordSet.addAll(searchResults.get(j + 1).getSearchResult());
                                    }
                                    else if (searchCoordsSize >= 4) {
                                        coordSet.add(searchResults.get(j).getSearchResultCoords(searchCoordsSize - 1));
                                        coordSet.add(searchResults.get(j + 1).getSearchResultCoords(searchCoordsSize1 - 1));
                                    }
                                })
                );

        return coordSet;
    }


}