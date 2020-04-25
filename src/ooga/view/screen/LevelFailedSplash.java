package ooga.view.screen;

import javafx.scene.Node;
import ooga.controller.ScreenController;

/**
 * Internal splash screen to display upon level failed.
 */
class LevelFailedSplash extends SplashScreen {

  LevelFailedSplash(ScreenController controller, Node parent) {
    super(controller, parent);

    this.getStyleClass().add("loading-screen");

    loadLayout();
  }

}
