package four_in_a_row.graphics;

import four_in_a_row.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameSpecificsValidation {

    public static boolean validationPasses(String playerOneSelection, String playerTwoSelection) {
        if ("player selection".equalsIgnoreCase(playerOneSelection) || "player selection".equalsIgnoreCase(playerTwoSelection)) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("alert.fxml"));
                Parent load = loader.load();
                AlertController controller = loader.getController();
                Stage stage = new Stage();
                controller.setStage(stage);
                Scene scene = new Scene(load);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}