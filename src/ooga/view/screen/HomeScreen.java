package ooga.view.screen;

import ooga.controller.ScreenController;

/**
 * Game Home screen, a hub between other menus.
 */
public class HomeScreen extends Screen {

  /**
   * Initializes HomeScreen, called via REFLECTION
   * @param controller ScreenController
   */
  public HomeScreen(ScreenController controller) {
    super(controller);
    loadLayout();
  }

  /**
   * Toggle dark mode. Used for REFLECTION.
   */
  protected void darkMode() {
    controller.toggleDarkMode();
  }

}
