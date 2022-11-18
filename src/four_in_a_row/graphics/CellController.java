package four_in_a_row.graphics;

import four_in_a_row.core.logic.win_logic.GameTableControl;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.core.structure.Token;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class CellController {

    @FXML
    public StackPane mainLayout;
    @FXML
    public Circle circle;

    private Pane relatedColumn;
    private Cell cell;

    public void updateCellGraphics(Token token, double wantedSize) {
        if (token == null) {

            int indexToRemove = (Table.HEIGHT - 1) - cell.getCoordinates().getY();
            ObservableList<Node> children = relatedColumn.getChildren();
            if (indexToRemove >= 0 && indexToRemove < children.size() && !relatedColumn.getChildren().isEmpty())
                this.relatedColumn.getChildren().remove(indexToRemove);

        } else {

            String tokenColorFileAlias = token.getColor().name().toLowerCase() + "_token.png";
            ImageView tokenImage = new ImageView(new Image(getClass().getResourceAsStream("/four_in_a_row/img/"+tokenColorFileAlias)));
            tokenImage.setFitWidth(wantedSize);
            tokenImage.setFitHeight(wantedSize);
            TokenAnimation animation = new TokenAnimation(tokenImage, relatedColumn, mainLayout);
            animation.animateToken();
        }
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setRelatedColumn(Pane relatedColumn) {
        this.relatedColumn = relatedColumn;
    }
}