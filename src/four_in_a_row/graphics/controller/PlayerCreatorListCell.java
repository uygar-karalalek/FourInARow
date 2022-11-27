package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Player;
import four_in_a_row.graphics.use_case.ParentAndControllerRetrieverUseCase;
import javafx.scene.control.ListCell;

public class PlayerCreatorListCell extends ListCell<Player> {

    @Override
    protected void updateItem(Player player, boolean empty) {
        super.updateItem(player, empty);

        if (empty) {
            setGraphic(null);
        } else if (player != null) {
            ParentAndControllerRetrieverUseCase<PlayerItemController> useCase = new ParentAndControllerRetrieverUseCase<>();
            ParentAndControllerRetrieverUseCase<PlayerItemController>.ParentControllerPair parentControllerPair =
                    useCase.getParentControllerPair("player_item.fxml");
            parentControllerPair.getController().setPlayer(player);
            setGraphic(parentControllerPair.getParent());
        } else {
            setText("null");
            setGraphic(null);
        }
    }

}