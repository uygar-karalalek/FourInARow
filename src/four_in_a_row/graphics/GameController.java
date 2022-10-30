package four_in_a_row.graphics;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.logic.Game;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.stream.IntStream;

public class GameController {

    public static final int BORDER_WIDTH = 5;

    @FXML public GridPane mainLayout;
    @FXML public GridPane graphicTable;
    @FXML public HBox columnsMouseDetectionPane;

    private Game game;

    public GameController() {
    }

    public void init() {
        createTable();
        graphicTable.widthProperty().addListener(this::onSizeChanged);
        graphicTable.heightProperty().addListener(this::onSizeChanged);

        IntStream.range(0, Table.WIDTH).forEach(col ->
                columnsMouseDetectionPane.getChildren().get(col)
                        .setOnMouseClicked(ev -> {
                            TableCoordinates coordinates = game.turnExecution(col);
                            // System.out.println(game.getGameTableControl().controlBasedOnPivot(coordinates).size());
                        }));
    }

    private void onSizeChanged(Observable observable) {
        double min = getTableMinimumSizeBoundary();
        columnsMouseDetectionPane.getChildren().forEach(node -> {
            if (node instanceof Pane)
                ((Pane) node).setPrefSize
                        (min / Table.WIDTH,
                        min / Table.WIDTH);
        });

        graphicTable.getChildren()
                .stream()
                .filter(node -> node instanceof StackPane)
                .map(node -> (StackPane) node)
                .forEach(this::updateItemSizeBasedOnTable);
    }

    public double getTokenSlotSize() {
        double min = getTableMinimumSizeBoundary();
        return (min / Table.HEIGHT) - BORDER_WIDTH;
    }

    private void updateItemSizeBasedOnTable(StackPane pane) {
        double prefSize = getTableMinimumSizeBoundary() / Table.WIDTH;
        double tokenSlotSize = getTokenSlotSize();
        pane.setPrefSize(prefSize, prefSize);

        Circle circle = (Circle) pane.getChildren().get(0);
        circle.setRadius(tokenSlotSize / 2);

        ImageView tokenImage = (ImageView) pane.getChildren().get(1);
        tokenImage.setFitWidth(tokenSlotSize);
        tokenImage.setFitHeight(tokenSlotSize);
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

                        cell.getItem().addListener((obs, token, newToken) -> cellController.updateCellGraphics(newToken, getTokenSlotSize() - 10));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void setGame(Game game) {
        this.game = game;
    }

}