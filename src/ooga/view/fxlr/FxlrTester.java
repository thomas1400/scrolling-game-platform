package ooga.view.fxlr;

import java.io.File;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.ScreenController;
import ooga.controller.data.BasicLevelList;
import ooga.controller.data.UserList;
import ooga.view.Screen;

public class FxlrTester extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      FXLRParser parser = new FXLRParser(new ScreenController(primaryStage, new UserList(), new BasicLevelList()));
      Screen root = parser.loadFXLRLayout(new File("resources/view/HomeScreen.fxlr"));
      primaryStage.setScene(new Scene(root));
//      for (Node child : root.getChildren()) {
//        System.out.println(child + " {");
//        try {
//          for (Node grandchild : (ObservableList<Node>) child.getClass().getMethod("getChildren").invoke(child)) {
//            System.out.println("\t" + grandchild);
//          }
//        } catch (Exception ignored) {}
//        System.out.println("}");
//      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
