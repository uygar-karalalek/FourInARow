package four_in_a_row.graphics;

import four_in_a_row.core.logic.TableCoordinates;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Token;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class CellController {

    @FXML
    public StackPane mainLayout;
    @FXML
    public Circle circle;

    public void updateCellGraphics(Token token, double wantedSize) {
        String tokenColorFileAlias = token.getColor().name().toLowerCase() + "_token.png";
        ImageView tokenImage = new ImageView(new Image(getClass().getResourceAsStream("/four_in_a_row/img/"+tokenColorFileAlias)));
        tokenImage.setFitWidth(wantedSize);
        tokenImage.setFitHeight(wantedSize);
        this.mainLayout.getChildren().add(tokenImage);
    }

}