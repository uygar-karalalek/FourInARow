package four_in_a_row.graphics.controller;

import four_in_a_row.core.logic.Player;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

public class PlayerItemController {

    @FXML
    public SVGPath pauseBtn, playBtn;
    @FXML
    public Label usernameLbl, timeLabel;
    @FXML
    public Slider audioSlider;
    public HBox mainLayout;

    private SimpleObjectProperty<Player> player = new SimpleObjectProperty<>();

    @FXML
    public void onEdit() {

    }

    public void setPlayer(Player player) {
        this.player.set(player);
        usernameLbl.setText(player.getName());
    }

}