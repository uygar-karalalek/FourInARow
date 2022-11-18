package four_in_a_row.graphics;

import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Token;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.util.Objects;

public class CellController {

    @FXML
    public StackPane mainLayout;
    @FXML
    public Circle circle;

    private Pane relatedColumn;
    private Cell cell;

    public void updateCellGraphics(Token token, double wantedSize) {
        if (token == null) {
            this.mainLayout.getChildren().remove(this.mainLayout.getChildren().size() - 1);
        } else {
            String tokenColorFileAlias = token.getColor().name().toLowerCase() + "_token.png";
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/four_in_a_row/img/" + tokenColorFileAlias));
            ImageView tokenImage = new ImageView(new Image(inputStream));

            tokenImage.setOpacity(1);
            tokenImage.setFitWidth(wantedSize);
            tokenImage.setFitHeight(wantedSize);

            TokenAnimation animation = new TokenAnimation(tokenImage, relatedColumn, mainLayout);
            animation.playAnimation();
        }
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setRelatedColumn(Pane relatedColumn) {
        this.relatedColumn = relatedColumn;
    }
}