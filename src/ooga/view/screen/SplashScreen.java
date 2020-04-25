package ooga.view.screen;

import javafx.scene.Node;
import ooga.controller.ScreenController;

/**
 * Class of screen that overlays on a parent Node. Used for loading screens, death screens, etc.
 */
public class SplashScreen extends Screen {

  /**
   * Initialize a SplashScreen with a parent and set the size to that of the parent.
   * @param controller ScreenController
   * @param parent parent
   */
  public SplashScreen(ScreenController controller, Node parent) {
    super(controller);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());
  }
}
