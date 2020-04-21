package ooga.view.screen;

import ooga.controller.ScreenController;

public class HelpScreen extends Screen {

  public HelpScreen(ScreenController controller) {
    super(controller);

    loadLayout();
    this.getStyleClass().add("help-screen");
  }
}
