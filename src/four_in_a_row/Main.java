package four_in_a_row;

import four_in_a_row.core.logic.Game;
import four_in_a_row.core.logic.Player;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Table;
import four_in_a_row.graphics.controller.GameController;
import four_in_a_row.graphics.use_case.ParentAndControllerRetrieverUseCase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ParentAndControllerRetrieverUseCase<GameController> useCase = new ParentAndControllerRetrieverUseCase<>();
        ParentAndControllerRetrieverUseCase<GameController>.ParentControllerPair parentControllerPair
                = useCase.getParentControllerPair("main.fxml");

        Game game = new Game(
                new Player(TokenColor.RED),
                new Player(TokenColor.BLUE),
                new Table());

        parentControllerPair.getController().setGame(game);
        parentControllerPair.getController().init();

        primaryStage.setScene(new Scene(parentControllerPair.getParent()));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}