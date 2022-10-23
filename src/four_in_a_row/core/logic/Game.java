package four_in_a_row.core.logic;

import four_in_a_row.core.logic.win_logic.GameTableControl;
import four_in_a_row.core.structure.Table;
import four_in_a_row.core.structure.Token;

public class Game {

    public Player
            firstPlayer,
            secondPlayer,
            currentPlayer;

    private final Token currTurnToken;
    private final Table gameTable;
    private GameTableControl gameTableControl;

    public Game(Player firstPlayer,
                Player second,
                Table gameTable) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = second;
        this.currentPlayer = firstPlayer;
        this.currTurnToken = new Token(firstPlayer.getColor());
        this.gameTable = gameTable;
        this.gameTableControl = new GameTableControl(this.gameTable);
    }

    public TableCoordinates turnExecution(int col) {
        TableCoordinates coordinates = gameTable.addTokenToCell(col, new Token(currentPlayer.getColor()));
        currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
        return coordinates;
    }

    public Token getTurnToken() {
        return currTurnToken;
    }

    public Token getCurrTurnToken() {
        return currTurnToken;
    }

    public Table getGameTable() {
        return gameTable;
    }

    public GameTableControl getGameTableControl() {
        return gameTableControl;
    }
}