package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Player;
import javafx.scene.control.ListCell;

public class PlayerCreatorListCell extends ListCell<Player> {

    @Override
    protected void updateItem(Player player, boolean empty) {
        super.updateItem(player, empty);

        if (empty) {
            setText(null);
            // Create the graphics
//            setGraphic(...);
        }
    }

}