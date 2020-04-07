package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;

public class ScreenTester extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {
    ScreenController sc = new ScreenController(new MainDisplayPane());
    primaryStage.setScene(new Scene(new UserSelectorScreen(sc)));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
