package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Player;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerItemController implements Initializable {

    @FXML
    public Button pauseBtn, playBtn, saveBtn, editBtn;
    @FXML
    public Label usernameLbl, timeLabel;
    @FXML
    public Slider audioSlider;
    @FXML
    public HBox viewLayout;

    private SimpleObjectProperty<Player> player = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewLayout.getChildren().remove(pauseBtn);
        viewLayout.getChildren().remove(editBtn);
    }

    @FXML
    public void onEdit() {
        // implement functionality that permits to edit a player (audio, username ...)

    }

    public void setPlayer(Player player) {
        this.player.set(player);
        usernameLbl.setText(player.getName());
    }

}