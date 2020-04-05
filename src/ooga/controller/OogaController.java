package ooga.controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ooga.model.data.User;
import ooga.view.MainDisplayPane;

public class OogaController {

  private static final int INITIAL_WINDOW_WIDTH = 720;
  private static final int INITIAL_WINDOW_HEIGHT = 540;

  private ScreenController myScreenController;

  public OogaController(Stage primaryStage){
    MainDisplayPane mainPane = createAppPane();

    ScreenController myScreenController = new ScreenController(mainPane);

    Scene myHomeScene = new Scene(mainPane, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);

    primaryStage.setScene(myHomeScene);
    primaryStage.show();
  }

  private MainDisplayPane createAppPane() {
    MainDisplayPane appPane = new MainDisplayPane();
    appPane.setPadding(new Insets(15, 15, 15, 15));
    return appPane;
  }

  private User createDefaultUser(){return null;}

  private ScreenController createScreenController(){return null;}
}
