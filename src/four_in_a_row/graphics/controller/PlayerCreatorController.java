package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PlayerCreatorController {

    @FXML
    public BorderPane mainLayout;
    public ListView<Player> players;
    private ObservableList<Player> playersList;

    @FXML
    public Label title;

    public void initialize() {
        this.players.setCellFactory(playerListView -> new PlayerCreatorListCell());
        this.players.getItems().add(new Player(null, "FDA"));
        this.players.getItems().add(new Player(null, "Ci"));
    }

    public void onCreatePlayer() {

    }

    public void onDeletePlayer() {

    }

    public void setPlayersList(ObservableList<Player> playersList) {
        this.playersList = playersList;

    }
}