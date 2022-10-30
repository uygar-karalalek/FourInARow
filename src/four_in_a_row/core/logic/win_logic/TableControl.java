package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cells;

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
        SearchResults searchResults = new SearchResults(
                initCoordsToCheck, controlFactors, controlColor, cells);

        IntStream.iterate(1, currEmptySearchObject -> searchResults.areControlsAvailable(),
                        currEmptySearchObject -> currEmptySearchObject + 1)

                .forEach(cycle -> IntStream.iterate(0,
                                currentSearchTypeIndex -> currentSearchTypeIndex < searchResults.getCoordSearchResults().size() - 1,
                                currentSearchTypeIndex -> currentSearchTypeIndex + 2)
                                .forEach(currentSearchTypeIndex -> searchResults.controlCoordinateCouple(currentSearchTypeIndex, cycle)));

        return searchResults.getCoordSet();
    }


}