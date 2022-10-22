package four_in_a_row.core.logic.win_logic;

public enum ControlFactorType {

    DIAGONAL_RIGHT(1, 1),
    DIAGONAL_LEFT(1, -1),
    HORIZONTAL(1, 0),
    VERTICAL(0, 1);

    private final int xFactor;
    private final int yFactor;
    private final int secondXFactor;
    private final int secondYFactor;

    ControlFactorType(int xFactor, int yFactor) {
        this.xFactor = xFactor;
        this.yFactor = yFactor;
        this.secondXFactor = -xFactor;
        this.secondYFactor = -yFactor;
    }

    public int getXFactor() {
        return xFactor;
    }

    public int getYFactor() {
        return yFactor;
    }

    public int getSecondXFactor() {
        return secondXFactor;
    }

    public int getSecondYFactor() {
        return secondYFactor;
    }
}