package four_in_a_row.graphics;

import four_in_a_row.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TokenImage extends ImageView {

    private static final int DEFAULT_FIT_WIDTH = 30;
    private static final int DEFAULT_FIT_HEIGHT = 30;

    public TokenImage(String imageName) {
        super(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/four_in_a_row/img/" + imageName))));
        this.setFitWidth(DEFAULT_FIT_WIDTH);
        this.setFitHeight(DEFAULT_FIT_HEIGHT);
    }
}