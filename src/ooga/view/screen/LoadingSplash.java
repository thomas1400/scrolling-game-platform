package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;

/**
 * Loading splash screen to show on level load.
 */
public class LoadingSplash extends SplashScreen {

  /**
   * Initializes a LoadingSplash for a given level.
   * @param controller ScreenController
   * @param parent screen to overlay
   * @param level level being loaded
   */
  public LoadingSplash(ScreenController controller, Node parent, BasicLevel level) {
    super(controller, parent);

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }

}
