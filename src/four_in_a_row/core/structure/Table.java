package four_in_a_row.core.structure;

import four_in_a_row.core.logic.TableCoordinates;

public class Table {

    private final Cells cells = new Cells();
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private final short[] piles = new short[WIDTH];

    { for (int i = 0; i < WIDTH; i++) piles[i] = HEIGHT - 1; }

    public Cells getCells() {
        return cells;
    }

    public Cell getCell(TableCoordinates tableCoordinates) {
        return cells.get(tableCoordinates.getX(), tableCoordinates.getY());
    }

    public void setCell(TableCoordinates coordinates, Cell cell) {
        cells.set(coordinates.getX(), coordinates.getY(), cell);
    }

    public TableCoordinates addTokenToCell(int col, Token token) {
        TableCoordinates coordinates = new TableCoordinates(col, piles[col]--);
        cells.get(coordinates.getX(), coordinates.getY()).setItem(token);
        return coordinates;
    }

}