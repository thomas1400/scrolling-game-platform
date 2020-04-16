package ooga;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.data.BasicLevelList;
import ooga.controller.data.UserList;
import ooga.view.screen.LevelBuilderScreen;

public class Tester extends Application{

  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    ScreenController sc = new ScreenController(primaryStage, new UserList(), new BasicLevelList());

    LevelBuilderScreen root = new LevelBuilderScreen(sc);

    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }



}

