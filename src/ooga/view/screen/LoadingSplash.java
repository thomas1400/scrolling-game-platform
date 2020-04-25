package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;

public class LoadingSplash extends SplashScreen {

  public LoadingSplash(ScreenController controller, Node parent, BasicLevel level) {
    super(controller, parent);

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }

  private void initializeLayout(BasicLevel level) {

  }

}
