package ooga.view.screen;

import javafx.scene.Node;
import ooga.controller.ScreenController;

public class SplashScreen extends Screen {

  public SplashScreen(ScreenController controller, Node parent) {
    super(controller);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());
  }
}
