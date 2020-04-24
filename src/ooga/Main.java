package ooga;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.OogaController;

public class Main extends Application {

    /**
     * Runs the entire OOGA Program by creating the OogaController from which the entire rest of
     * the game is initialized, and the main window is displayed
     */
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * start! :D
     * @param primaryStage
     * @throws FileNotFoundException
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        new OogaController(primaryStage);
    }
}
