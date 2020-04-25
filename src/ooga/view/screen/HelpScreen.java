package ooga.view.screen;

import ooga.controller.ScreenController;

/**
 * Game-specific Help screen that shows game-related information and instructions.
 */
public class HelpScreen extends Screen {

  /**
   * Initialized via REFLECTION
   * @param controller ScreenController
   */
  public HelpScreen(ScreenController controller) {
    super(controller);

    loadLayout();
    this.getStyleClass().add("help-screen");
  }
}
