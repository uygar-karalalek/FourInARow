package four_in_a_row.graphics;

import four_in_a_row.core.logic.Player;
import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.Game;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.data.ApplicationProperties;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
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
    public GridPane mainLayout;
    @FXML
    public GridPane graphicTable;
    @FXML
    public HBox columnsMouseDetectionPane;
    @FXML
    public ChoiceBox<String> playerOneNameBox;
    @FXML
    public ChoiceBox<String> playerTwoNameBox;

    @FXML
    public VBox leftBar;

    public Label player1NameLabel, player2NameLabel;

    @FXML
    public BorderPane gameTablePane;

    private Game game;

    public void init() {
        createTable();

        String[] firstPlayerName = ApplicationProperties.getProperty("firstPlayerName").split(",");
        String[] secondPlayerName = ApplicationProperties.getProperty("secondPlayerName").split(",");

        playerOneNameBox.setItems(FXCollections.observableArrayList(firstPlayerName));
        playerTwoNameBox.setItems(FXCollections.observableArrayList(secondPlayerName));

        playerOneNameBox.getSelectionModel().select(0);
        playerTwoNameBox.getSelectionModel().select(0);

        graphicTable.widthProperty().addListener(this::onSizeChanged);
        graphicTable.heightProperty().addListener(this::onSizeChanged);

        IntStream.range(0, Table.WIDTH).forEach(col ->
                columnsMouseDetectionPane.getChildren().get(col)
                        .setOnMouseClicked(ev -> {
                            TableCoordinates coordinates = game.turnExecution(col);
                            Player current = game.getCurrentPlayer();
                            Set<TableCoordinates> tableCoordinates =
                                    game.getGameTableControl().controlBasedOnPivot(coordinates);
                            if (tableCoordinates.size() >= 4) {
                                System.out.println("Player " + current.getName() +" won!");
                            }
                        }));
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
                ((ImageView) node).setFitWidth(tokenSlotSize - BORDER_WIDTH);
                ((ImageView) node).setFitHeight(tokenSlotSize - BORDER_WIDTH);
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

                        cell.getItem().addListener((obs, token, newToken) ->
                                cellController.updateCellGraphics(newToken, getTokenSlotSize() - 10));

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
        String player1Name = this.playerOneNameBox.getValue();
        this.game.firstPlayer.setName(player1Name);
        String player2Name = this.playerTwoNameBox.getValue();
        this.game.secondPlayer.setName(player2Name);

        this.player1NameLabel = new Label(player1Name);
        this.player2NameLabel = new Label(player2Name);

        this.leftBar.getChildren().remove(0, 3);
        this.leftBar.getChildren().addAll(0, List.of(this.player1NameLabel, this.player2NameLabel));

        this.gameTablePane.setDisable(false);
    }

    @FXML
    public void onCreatePlayer() {

    }

}
