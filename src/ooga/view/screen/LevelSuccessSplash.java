package ooga.view.screen;

import javafx.scene.Node;
import ooga.controller.ScreenController;

/**
 * Internal splash screen to display upon level success.
 */
class LevelSuccessSplash extends SplashScreen {

  LevelSuccessSplash(ScreenController controller, Node parent) {
    super(controller, parent);

    this.getStyleClass().add("loading-screen");

    loadLayout();
  }
}
