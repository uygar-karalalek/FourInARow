package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.Coordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Table;

public class GameTableControl {

    private final Table gameTable;

    public GameTableControl(Table gameTable) {
        this.gameTable = gameTable;
    }

    public boolean controlBasedOnPivot(Coordinates coordinates) {
        TokenColor tokenColor = gameTable.getCells()[coordinates.getY()][coordinates.getX()].getItem().get().getColor();
        boolean horizontalWin = horizontalControl(coordinates.getY(), coordinates.getX(), tokenColor);
        boolean verticalWin = verticalWin(coordinates.getY(), coordinates.getX(), tokenColor);
        boolean diagonalUpLeftDownRightWin = diagonalUpLeftDownRightWin (coordinates.getY(), coordinates.getX(), tokenColor);
        boolean diagonalUpRightDownLeftWin = diagonalUpRightDownLeftWin(coordinates.getY(), coordinates.getX(), tokenColor);
        return horizontalWin || verticalWin || diagonalUpLeftDownRightWin || diagonalUpRightDownLeftWin;
    }

    private boolean diagonalUpLeftDownRightWin(int row, int col, TokenColor tokenColor) {
        boolean upLeft = true, downRight = true;
        int i = 1, count = 1;
        while(upLeft || downRight) {

            if (row - i <= -1 || col - i <= -1)
                upLeft = false;
            else if (gameTable.getCells()[row - i][col - i].getItem().get() != null &&
                    gameTable.getCells()[row - i][col - i].getItem().get().getColor() == tokenColor)
                count++;
            else upLeft = false;

            if (row + i >= Table.HEIGHT || col + i >= Table.WIDTH) downRight = false;
            else if (gameTable.getCells()[row + i][col + i].getItem().get() != null &&
                    gameTable.getCells()[row + i][col + i].getItem().get().getColor() == tokenColor)
                count++;
            else downRight = false;

            i++;
        }

        return count >= 4;
    }

    private boolean diagonalUpRightDownLeftWin(int row, int col, TokenColor tokenColor) {
        boolean upRight = true, downLeft = true;
        int i = 1, count = 1;
        while(upRight || downLeft) {

            if (row - i <= -1 || col + i >= Table.WIDTH)
                upRight = false;
            else if (gameTable.getCells()[row - i][col + i].getItem().get() != null &&
                    gameTable.getCells()[row - i][col + i].getItem().get().getColor() == tokenColor)
                count++;
            else upRight = false;

            if (row + i >= Table.HEIGHT || col - i >= Table.WIDTH) downLeft = false;
            else if (gameTable.getCells()[row + i][col - i].getItem().get() != null &&
                    gameTable.getCells()[row + i][col - i].getItem().get().getColor() == tokenColor)
                count++;
            else downLeft = false;

            i++;
        }

        return count >= 4;
    }

    private boolean verticalWin(int row, int col, TokenColor tokenColor) {
        boolean upSearch = true, downSearch = true;
        int i = 1, count = 1;
        while(upSearch || downSearch) {

            if (row - i <= -1) upSearch = false;
            else if (gameTable.getCells()[row - i][col].getItem().get() != null &&
                    gameTable.getCells()[row - i][col].getItem().get().getColor() == tokenColor) count++;
            else upSearch = false;

            if (row + i >= Table.HEIGHT) downSearch = false;
            else if (gameTable.getCells()[row + i][col].getItem().get() != null &&
                    gameTable.getCells()[row + i][col].getItem().get().getColor() == tokenColor) count++;
            else downSearch = false;

            i++;
        }

        return count >= 4;
    }

    private boolean horizontalControl(int row, int col, TokenColor tokenColor) {
        boolean leftSearch = true, rightSearch = true;
        int i = 1, count = 1;
        while(leftSearch || rightSearch) {

            if (col - i <= -1) leftSearch = false;
            else if (gameTable.getCells()[row][col - i].getItem().get() != null &&
                    gameTable.getCells()[row][col - i].getItem().get().getColor() == tokenColor) count++;
            else leftSearch = false;

            if (col + i >= Table.WIDTH) rightSearch = false;
            else if (gameTable.getCells()[row][col + i].getItem().get() != null &&
                    gameTable.getCells()[row][col + i].getItem().get().getColor() == tokenColor) count++;
            else rightSearch = false;

            i++;
        }

        return count >= 4;
    }

}