package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;

public class ScreenTester extends Application {


  @Override
  public void start(Stage primaryStage) {
    ScreenController sc = new ScreenController(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
