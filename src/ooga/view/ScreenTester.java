package ooga.view;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.model.data.BasicLevelList;
import ooga.model.data.UserList;

public class ScreenTester extends Application {


  @Override
  public void start(Stage primaryStage) {
    new ScreenController(primaryStage, new UserList(), new BasicLevelList());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
