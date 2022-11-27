package four_in_a_row.graphics;

import four_in_a_row.graphics.controller.AlertController;
import four_in_a_row.graphics.use_case.ParentAndControllerRetrieverUseCase;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameSpecificsValidation {

    public static boolean validationPasses(String playerOneSelection, String playerTwoSelection) {
        if ("player selection".equalsIgnoreCase(playerOneSelection) || "player selection".equalsIgnoreCase(playerTwoSelection)) {
            ParentAndControllerRetrieverUseCase<AlertController> useCase = new ParentAndControllerRetrieverUseCase<>();
            ParentAndControllerRetrieverUseCase<AlertController>.ParentControllerPair parentControllerPair =
                    useCase.getParentControllerPair("alert.fxml");
            Stage stage = new Stage();
            parentControllerPair.getController().setStage(stage);
            Scene scene = new Scene(parentControllerPair.getParent());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
            return false;
        }
        return true;
    }

}