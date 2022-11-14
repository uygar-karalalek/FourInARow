package four_in_a_row.graphics;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlayerNameBoxRepresentation extends HBox {

    public static final int DEFAULT_SPACING = 10;

    public PlayerNameBoxRepresentation(String playerName, String tokenImgName, String cssPlayerNameId) {
        super(DEFAULT_SPACING, new TokenImage(tokenImgName), new Label(playerName) { { this.setId(cssPlayerNameId); } });
    }

}