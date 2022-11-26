package four_in_a_row.graphics;

import four_in_a_row.graphics.controller.GameController;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TokenAnimation {

    private final ImageView representedToken;
    private final Pane relatedColumn;
    private final StackPane endNode;
    private TranslateTransition animation;

    public TokenAnimation(ImageView representedToken, Pane pathSection, StackPane endNode) {
        this.representedToken = representedToken;
        this.relatedColumn = pathSection;
        this.endNode = endNode;
        setupAnimation();
    }

    public void playAnimation() {
        placeCorrectlyTheObjects();
        animation.play();
    }

    private void setupAnimation() {
        animation = new TranslateTransition();
        animation.setNode(representedToken);
        animation.setToY(endNode.getLayoutY() + GameController.BORDER_WIDTH);
        animation.setInterpolator(Interpolator.EASE_IN);

        animation.setOnFinished(actionEvent -> {
            // Since at the end, the token will be within a
            // different Y transition, this line of code setups
            // the initial value of all Nodes translateY property, that is, zero
            representedToken.setTranslateY(0);
            relatedColumn.getChildren().remove(representedToken);
            endNode.getChildren().add(representedToken);
        });
    }

    private void placeCorrectlyTheObjects() {
        relatedColumn.getChildren().add(representedToken);
        representedToken.setLayoutX(GameController.BORDER_WIDTH);
        representedToken.setLayoutY(0);
    }

}