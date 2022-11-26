package four_in_a_row;

import four_in_a_row.core.logic.Game;
import four_in_a_row.core.logic.Player;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Table;
import four_in_a_row.graphics.controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent load = loader.load();
        GameController controller = loader.getController();

        Game game = new Game(
                new Player(TokenColor.RED),
                new Player(TokenColor.BLUE),
                new Table());

        controller.setGame(game);
        controller.init();

        primaryStage.setScene(new Scene(load));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}