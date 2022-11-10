package four_in_a_row.core.logic;

import four_in_a_row.core.logic.win_logic.GameTableControl;
import four_in_a_row.core.structure.Table;
import four_in_a_row.core.structure.Token;

import java.util.stream.IntStream;

public class Game {

    private boolean playing = false;
    public Player
            firstPlayer,
            secondPlayer,
            currentPlayer;

    private Token currTurnToken;
    private Table gameTable;
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

    public Player getCurrentPlayer() {
        return this.currentPlayer;
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

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void resetGame() {
        this.currentPlayer.setName(null);
        this.firstPlayer.setName(null);
        this.secondPlayer.setName(null);
        this.playing = false;
        this.currTurnToken = null;

        IntStream.range(0, 6).forEach(row ->
                IntStream.range(0, 7).forEach(col -> {
                    this.gameTable.removeTokenFromColumn(row, col);
                }));

    }

}