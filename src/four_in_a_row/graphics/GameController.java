package four_in_a_row.graphics;

import four_in_a_row.core.logic.Coordinates;
import four_in_a_row.core.logic.Game;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
                            Coordinates coordinates = game.turnExecution(col);
                            System.out.println(game.getGameTableControl().controlBasedOnPivot(coordinates));
                        }));
    }

    private void onSizeChanged(Observable observable) {
        double min = Math.min(graphicTable.getWidth(), graphicTable.getHeight());
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

    private void updateItemSizeBasedOnTable(StackPane pane) {
        double min = Math.min(graphicTable.getWidth(), graphicTable.getHeight());
        pane.setPrefSize(min / Table.WIDTH, min / Table.WIDTH);
        Circle circle = (Circle) pane.getChildren().get(0);
        circle.setRadius((min / (Table.HEIGHT * 2)) - BORDER_WIDTH);
    }

    private void createTable() {
        IntStream.range(0, 6).forEach(row ->
                IntStream.range(0, 7).forEach(col -> {
                    try {

                        Coordinates coordinates = new Coordinates(col, row);
                        FXMLLoader loader = new FXMLLoader();
                        loader.load(getClass().getResourceAsStream("../cell.fxml"));

                        CellController cellController = loader.getController();
                        updateItemSizeBasedOnTable(cellController.mainLayout);

                        graphicTable.add(cellController.mainLayout, coordinates.getX(), coordinates.getY());
                        Cell cell = new Cell(coordinates);
                        game.getGameTable().getCells()[coordinates.getY()][coordinates.getX()] = cell;
                        cell.getItem().addListener((obs, token, newToken) -> {
                            if (newToken.getColor() == TokenColor.RED)
                                cellController.circle.setFill(Color.RED);
                            else
                                cellController.circle.setFill(Color.YELLOW);
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void setGame(Game game) {
        this.game = game;
    }

}