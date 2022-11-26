package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Game;
import four_in_a_row.core.logic.Player;
import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.data.ApplicationProperties;
import four_in_a_row.graphics.GameSpecificsValidation;
import four_in_a_row.graphics.PlayerNameBoxRepresentation;
import four_in_a_row.graphics.PlayerSelector;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class GameController {

    public static final int BORDER_WIDTH = 5;

    @FXML
    public GridPane mainLayout, graphicTable;
    @FXML
    public HBox columnsMouseDetectionPane;
    @FXML
    public PlayerSelector playerOneNameBox, playerTwoNameBox;
    @FXML
    public VBox leftBar;
    @FXML
    public BorderPane gameTablePane;
    @FXML
    public Button playButton;
    @FXML
    public Label winnerLabel, playerDescriptor;

    public HBox player1NameLabelBox, player2NameLabelBox;

    private Game game;

    public void init() {
        createTable();

        String[] playerNames = ApplicationProperties.getProperty("firstPlayerName").split(",");

        playerOneNameBox.setPlayerNames(playerNames);
        playerTwoNameBox.setPlayerNames(playerNames);
        playerOneNameBox.initializeSelector(playerTwoNameBox);
        playerTwoNameBox.initializeSelector(playerOneNameBox);

        graphicTable.widthProperty().addListener(this::onSizeChanged);
        graphicTable.heightProperty().addListener(this::onSizeChanged);

        graphicTable.getChildren().forEach(node -> node.setOpacity(0.5));
        IntStream.range(0, Table.WIDTH).forEach(col ->
                columnsMouseDetectionPane.getChildren().get(col).setOnMouseClicked(ev -> onMouseClicked(col)));
    }

    private void onMouseClicked(int clickedColumn) {
        Player current = game.getCurrentPlayer();

        // If the table has an item at the top of the stack of a column, that means the column is full,
        // then it is impossible to add more tokens!
        if (game.getGameTable().getCells().get(clickedColumn, 0).getItem().get() != null) return;

        TableCoordinates coordinates = game.turnExecution(clickedColumn);
        Set<TableCoordinates> tableCoordinates = game.getGameTableControl().controlBasedOnPivot(coordinates);
        if (tableCoordinates.size() >= 4) {
            onPlayerWinning(current);

            graphicTable.getChildren().forEach(node -> node.setOpacity(0.5));
            tableCoordinates.forEach(winningCoords -> {
                StackPane cell = (StackPane) graphicTable.getChildren().get(winningCoords.toGridAbsoluteValue());
                cell.setOpacity(1);
            });
        }
    }

    private void onPlayerWinning(Player current) {
        this.winnerLabel.setOpacity(1);
        this.winnerLabel.setText("Player " + current.getName() + " won!");
        this.gameTablePane.setDisable(true);
    }

    private void onSizeChanged(Observable observable) {
        double min = getTableMinimumSizeBoundary();
        columnsMouseDetectionPane.getChildren().forEach(node -> {
            if (node instanceof Pane)
                ((Pane) node).setPrefSize(min / Table.WIDTH, min / Table.WIDTH);
        });

        graphicTable.getChildren()
                .stream()
                .filter(node -> node instanceof StackPane)
                .map(node -> (StackPane) node)
                .forEach(this::updateItemSizeBasedOnTable);
    }

    public double getTokenSlotSize() {
        double min = getTableMinimumSizeBoundary();
        return (min / Table.WIDTH);
    }

    private void updateItemSizeBasedOnTable(StackPane pane) {
        double tokenSlotSize = getTokenSlotSize();
        pane.setPrefSize(tokenSlotSize, tokenSlotSize);

        pane.getChildren().forEach(node -> {
            if (node instanceof Circle)
                ((Circle) node).setRadius(tokenSlotSize / 2 - BORDER_WIDTH);
            else if (node instanceof ImageView) {
                ((ImageView) node).setFitWidth(tokenSlotSize - BORDER_WIDTH * 2);
                ((ImageView) node).setFitHeight(tokenSlotSize - BORDER_WIDTH * 2);
            }
        });

    }

    private double getTableMinimumSizeBoundary() {
        return Math.min(graphicTable.getWidth(), graphicTable.getHeight());
    }

    private void createTable() {
        IntStream.range(0, 6).forEach(row ->
                IntStream.range(0, 7).forEach(col -> {
                    try {

                        TableCoordinates coordinates = new TableCoordinates(col, row);
                        Cell cell = new Cell(coordinates);

                        FXMLLoader loader = new FXMLLoader();
                        loader.load(getClass().getResourceAsStream("../cell.fxml"));

                        CellController cellController = loader.getController();
                        cellController.setCell(cell);
                        cellController.setRelatedColumn((Pane) columnsMouseDetectionPane.getChildren().get(col));

                        graphicTable.add(cellController.mainLayout, coordinates.getX(), coordinates.getY());

                        updateItemSizeBasedOnTable(cellController.mainLayout);

                        game.getGameTable().setCell(coordinates, cell);

                        cell.getItem().addListener((obs, token, newToken) ->
                                cellController.updateCellGraphics(newToken, getTokenSlotSize() - BORDER_WIDTH * 2));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @FXML
    public void onPlayClicked() {
        this.winnerLabel.setOpacity(0);

        if (this.game.isPlaying()) endGame();
        else beginGame();
    }

    private void beginGame() {

        this.playerDescriptor.setText("Players are");

        String player1Name = this.playerOneNameBox.getValue();
        String player2Name = this.playerTwoNameBox.getValue();

        if (GameSpecificsValidation.validationPasses(player1Name, player2Name)) {
            this.game.firstPlayer.setName(player1Name);
            this.game.secondPlayer.setName(player2Name);

            this.player1NameLabelBox = new PlayerNameBoxRepresentation(player1Name, "red_token.png", "player1NameLabel");
            this.player2NameLabelBox = new PlayerNameBoxRepresentation(player2Name, "blue_token.png", "player2NameLabel");

            this.leftBar.getChildren().remove(1, 3);
            this.leftBar.getChildren().addAll(1, List.of(this.player1NameLabelBox, this.player2NameLabelBox));

            this.playButton.setText("Play again");
            this.gameTablePane.setDisable(false);
            this.graphicTable.getChildren().forEach(node -> node.setOpacity(1));

            this.game.setPlaying(true);
        }
    }

    public void endGame() {
        this.game.resetGame();
        this.playerDescriptor.setText("Choose your player");
        this.playButton.setText("Play");
        this.leftBar.getChildren().remove(1, 3);
        this.leftBar.getChildren().addAll(1, List.of(this.playerOneNameBox, this.playerTwoNameBox));
        this.gameTablePane.setDisable(true);
        this.graphicTable.getChildren().forEach(node -> node.setOpacity(0.5));
    }

    @FXML
    public void onCreatePlayer() {

    }

}
