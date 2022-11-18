package four_in_a_row.graphics;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TokenAnimation {

    private ImageView representedToken;
    private Pane relatedColumn;
    private StackPane endNode;

    public TokenAnimation(ImageView representedToken, Pane pathSection, StackPane endNode) {
        this.representedToken = representedToken;
        this.relatedColumn = pathSection;
        this.endNode = endNode;
    }

    public void animateToken() {
        relatedColumn.getChildren().add(representedToken);
        representedToken.setOpacity(1);
        representedToken.setLayoutX(GameController.BORDER_WIDTH);
        representedToken.setLayoutY(0);
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(representedToken);
        translateTransition.setToY(endNode.getLayoutY() + GameController.BORDER_WIDTH);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.play();
    }

}