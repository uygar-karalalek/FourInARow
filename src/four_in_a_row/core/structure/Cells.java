package four_in_a_row.core.structure;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.TokenColor;

public class Cells {

    private Cell[][] cells = new Cell[Table.HEIGHT][Table.WIDTH];

    public Cell get(int x, int y) {
        return cells[y][x];
    }

    public void set(int x, int y, Cell cell) {
        cells[y][x] = cell;
    }

    public boolean isCellNotEmpty(TableCoordinates coordinates) {
        return get(coordinates.getX(), coordinates.getY()).getItem().get() != null;
    }

    public TokenColor getCellColor(int x, int y) {
        return get(x, y).getItem().get().getColor();
    }

}