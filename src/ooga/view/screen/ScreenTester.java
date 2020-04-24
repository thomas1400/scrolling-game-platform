package ooga.view.screen;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;

public class ScreenTester extends Application {


  @Override
  public void start(Stage primaryStage) {
    new ScreenController(primaryStage, new UserList());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
