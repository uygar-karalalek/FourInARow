package four_in_a_row.graphics;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AlertController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onClose() {
        this.stage.hide();
    }

}