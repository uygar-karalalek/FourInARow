package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cells;

import java.util.ArrayList;
import java.util.List;

public class CoordinateSearchResult {

    private final ControlFactorType controlFactorType;
    private final TokenColor tokenColor;
    private final List<TableCoordinates> searchResult = new ArrayList<>();
    private boolean controlAvailable = true;

    public CoordinateSearchResult(ControlFactorType controlFactorType, TokenColor tokenColor) {
        this.controlFactorType = controlFactorType;
        this.tokenColor = tokenColor;
    }

    public boolean isControlAvailable() {
        return controlAvailable;
    }

    public void setControlAvailable(boolean controlAvailable) {
        this.controlAvailable = controlAvailable;
    }

    public List<TableCoordinates> getSearchResult() {
        return searchResult;
    }

    public void control(Cells cells, TableCoordinates start, int incrNumber) {

        int row = start.getY(), col = start.getX();

        int xIncrement = this.controlFactorType.getXFactor() * incrNumber;
        int yIncrement = this.controlFactorType.getYFactor() * incrNumber;
        int newX = col + xIncrement, newY = row + yIncrement;

        TableCoordinates coordinates = new TableCoordinates(newX, newY);

        if (TableCoordinates.areXCoordsValid(newX)
                && TableCoordinates.areYCoordsValid(newY)
                && cells.isCellNotEmpty(coordinates)
                && cells.getCellColor(newX, newY) == this.tokenColor) this.searchResult.add(coordinates);

        else controlAvailable = false;

    }

}