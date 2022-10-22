package four_in_a_row.core.structure;

import four_in_a_row.core.logic.Coordinates;

public class Table {

    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private Cell[][] cells = new Cell[HEIGHT][WIDTH];
    private short[] piles = new short[WIDTH];

    { for (int i = 0; i < WIDTH; i++) piles[i] = HEIGHT - 1; }

    public Cell[][] getCells() {
        return cells;
    }
    public short[] getPiles() {
        return piles;
    }

    public Coordinates addTokenToCell(int col, Token token) {
        Coordinates coordinates = new Coordinates(col, piles[col]--);
        cells[coordinates.getY()][coordinates.getX()].setItem(token);
        return coordinates;
    }

}