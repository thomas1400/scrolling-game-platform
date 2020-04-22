package ooga.view.fxlr;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevelList;
import ooga.controller.users.UserList;
import ooga.view.screen.HomeScreen;
import ooga.view.screen.Screen;

public class FxlrTester extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      ScreenController sc = new ScreenController(primaryStage, new UserList(), new BasicLevelList());
      Screen root = new HomeScreen(sc);
      new FXLRParser().loadFXLRLayout(new HomeScreen(sc), new File("resources/view/LevelSelectorScreen.fxlr"));
      root.getStylesheets().add(new File("resources/stylesheet.css").toURI().toString());
      primaryStage.setScene(new Scene(root));
    } catch (Exception e) {
      //todo GET RID OF THIS PRINT STACK TRACE
      e.printStackTrace();
    }
  }
}
