package four_in_a_row.graphics;

import four_in_a_row.core.logic.Player;
import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.Game;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.data.ApplicationProperties;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    public GridPane mainLayout;
    @FXML
    public GridPane graphicTable;
    @FXML
    public HBox columnsMouseDetectionPane;
    @FXML
    public PlayerSelector playerOneNameBox;
    @FXML
    public PlayerSelector playerTwoNameBox;
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

        IntStream.range(0, Table.WIDTH).forEach(col ->
                columnsMouseDetectionPane.getChildren().get(col)
                        .setOnMouseClicked(ev -> {
                            Player current = game.getCurrentPlayer();
                            TableCoordinates coordinates = game.turnExecution(col);
                            Set<TableCoordinates> tableCoordinates =
                                    game.getGameTableControl().controlBasedOnPivot(coordinates);
                            if (tableCoordinates.size() >= 4) {
                                this.winnerLabel.setOpacity(1);
                                this.winnerLabel.setText("Player " + current.getName() +" won!");
                                this.gameTablePane.setDisable(true);
                                this.gameTablePane.setOpacity(0.5);
                            }
                        }));
    }

    private void deleteSelectedItem(ChoiceBox<String> otherBox, String item) {
        otherBox.getItems().remove(item);
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
                        FXMLLoader loader = new FXMLLoader();
                        loader.load(getClass().getResourceAsStream("../cell.fxml"));

                        CellController cellController = loader.getController();

                        graphicTable.add(cellController.mainLayout, coordinates.getX(), coordinates.getY());

                        updateItemSizeBasedOnTable(cellController.mainLayout);

                        Cell cell = new Cell(coordinates);
                        game.getGameTable().setCell(coordinates, cell);

                        cell.getItem().addListener((obs, token, newToken) -> {
                            cellController.updateCellGraphics(newToken, getTokenSlotSize() - BORDER_WIDTH * 2);
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @FXML
    public void onPlay() {
        this.winnerLabel.setOpacity(0);

        if (this.game.isPlaying()) {
            playIsFinished();
            this.playerDescriptor.setText("Choose your player");
            this.leftBar.getChildren().remove(1, 3);
            this.leftBar.getChildren().addAll(1, List.of(this.playerOneNameBox, this.playerTwoNameBox));
            this.gameTablePane.setDisable(true);
            this.gameTablePane.setOpacity(0.29);
        }
        else initializeGame();
    }

    private void initializeGame() {
        this.playerDescriptor.setText("Players are");

        String player1Name = this.playerOneNameBox.getValue();
        this.game.firstPlayer.setName(player1Name);
        String player2Name = this.playerTwoNameBox.getValue();
        this.game.secondPlayer.setName(player2Name);

        this.player1NameLabelBox = new HBox(10,
                new ImageView(new Image(getClass().getResourceAsStream("/four_in_a_row/img/red_token.png"))) {
                    {
                        this.setFitWidth(30);
                        this.setFitHeight(30);
                    }
                },
                new Label(player1Name) { { this.setId("player1NameLabel"); } });
        this.player2NameLabelBox = new HBox(10,
                new ImageView(new Image(getClass().getResourceAsStream("/four_in_a_row/img/blue_token.png"))) {
                    {
                        this.setFitWidth(30);
                        this.setFitHeight(30);
                    }
                },
                new Label(player2Name) { { this.setId("player2NameLabel"); } });

        this.leftBar.getChildren().remove(1, 3);
        this.leftBar.getChildren().addAll(1, List.of(this.player1NameLabelBox, this.player2NameLabelBox));

        this.playButton.setText("Play again");
        this.gameTablePane.setDisable(false);
        this.gameTablePane.setOpacity(1);

        this.game.setPlaying(true);
    }

    public void playIsFinished() {
        this.game.resetGame();
    }

    @FXML
    public void onCreatePlayer() {

    }

}
