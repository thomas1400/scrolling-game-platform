package ooga.view.screen;

import javafx.scene.Node;
import ooga.controller.ScreenController;

public class LevelSuccessSplash extends SplashScreen {

  public LevelSuccessSplash(ScreenController controller, Node parent) {
    super(controller, parent);

    this.getStyleClass().add("loading-screen");

    loadLayout();
  }
}
