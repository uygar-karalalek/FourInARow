package four_in_a_row.core.logic;

import four_in_a_row.core.structure.Table;

import java.util.Objects;

public class TableCoordinates {

    private int x, y;

    public TableCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static boolean areXCoordsValid(int x) {
        return x >= 0 && x < Table.WIDTH;
    }

    public static boolean areYCoordsValid(int y) {
        return y >= 0 && y < Table.HEIGHT;
    }

    public int toGridAbsoluteValue() {
        return this.getX() + Table.WIDTH * this.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableCoordinates that = (TableCoordinates) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}