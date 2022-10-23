package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Table;

import java.util.Set;

public class GameTableControl {

    private final Table gameTable;

    public GameTableControl(Table gameTable) {
        this.gameTable = gameTable;
    }

    public Set<TableCoordinates> controlBasedOnPivot(TableCoordinates coordinates) {
        TokenColor tokenColor = gameTable.getCell(coordinates).getItem().get().getColor();
        TableControl tableControl = new TableControl(gameTable.getCells(), tokenColor);
        return tableControl.controlFourInARow(coordinates, ControlFactorType.values());
    }

}