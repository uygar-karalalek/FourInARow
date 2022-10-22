package four_in_a_row.graphics;

import four_in_a_row.core.logic.Coordinates;
import four_in_a_row.core.structure.Cell;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class CellController {

    @FXML
    public StackPane mainLayout;
    @FXML
    public Circle circle;

    private Cell cell = new Cell(new Coordinates(-1, -1));

    public void setCell(Cell cell) {
        this.cell = cell;
    }

}