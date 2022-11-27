package four_in_a_row.graphics.use_case;

import four_in_a_row.graphics.controller.PlayerCreatorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ParentAndControllerRetrieverUseCase<CONTROLLER_TYPE> {

    public ParentControllerPair getParentControllerPair(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/"+fxmlFileName));
            Parent load = loader.load();
            CONTROLLER_TYPE controller = loader.getController();
            return new ParentControllerPair(load, controller);
        } catch (IOException e) {
            return new ParentControllerPair(null, null);
        }
    }

    public class ParentControllerPair {

        private Parent parent;
        private CONTROLLER_TYPE controller;

        public ParentControllerPair(Parent parent, CONTROLLER_TYPE controller) {
            this.parent = parent;
            this.controller = controller;
        }

        public Parent getParent() {
            return parent;
        }

        public CONTROLLER_TYPE getController() {
            return controller;
        }
    }

}