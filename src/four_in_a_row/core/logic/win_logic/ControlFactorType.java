package four_in_a_row.core.logic.win_logic;

public enum ControlFactorType {

    TO_TOP_RIGHT(1, -1),
    TO_BOTTOM_LEFT(-1, 1),
    TO_TOP_LEFT(-1, -1),
    TO_BOTTOM_RIGHT(1, 1),
    TO_RIGHT(1, 0),
    TO_LEFT(-1, 0),
    TO_TOP(0, -1),
    TO_DOWN(0, 1);

    private final int xFactor;
    private final int yFactor;

    ControlFactorType(int xFactor, int yFactor) {
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    public int getXFactor() {
        return xFactor;
    }

    public int getYFactor() {
        return yFactor;
    }

}