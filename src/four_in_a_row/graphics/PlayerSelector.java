package four_in_a_row.graphics;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class PlayerSelector extends ChoiceBox<String> {

    private String[] playerNames;
    private boolean modifying = false;
    private int lastEliminatedIndex = Integer.MAX_VALUE;

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
        this.setItems(FXCollections.observableArrayList(playerNames));
        this.getSelectionModel().select(0);
    }

    public void initializeSelector(PlayerSelector otherPlayerSelector) {
        this.getSelectionModel().selectedIndexProperty().addListener((obsIndex, oldIndex, newIndex) -> {
            // I am using this statement to overcome the possibility to cause
            // an infinite loop whenever the selection index changes when you add or remove an item
            if (!modifying) {
                otherPlayerSelector.modifying = true;
                if (oldIndex.intValue() > 0) {
                    int arrayNewIndex = oldIndex.intValue() >= lastEliminatedIndex ? oldIndex.intValue() + 1 : oldIndex.intValue();
                    otherPlayerSelector.getItems().add(arrayNewIndex, playerNames[arrayNewIndex]);

                    if (otherPlayerSelector.getItems().size() == playerNames.length)
                        otherPlayerSelector.lastEliminatedIndex = Integer.MAX_VALUE;
                }
                if (newIndex.intValue() > 0) {
                    int arrayNewIndex = newIndex.intValue() >= lastEliminatedIndex ? newIndex.intValue() + 1 : newIndex.intValue();
                    otherPlayerSelector.getItems().remove(playerNames[arrayNewIndex]);
                    otherPlayerSelector.lastEliminatedIndex = arrayNewIndex;
                }
                otherPlayerSelector.modifying = false;
            }
        });
    }

}