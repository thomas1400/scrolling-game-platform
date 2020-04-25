package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;

public class LoadingScreen extends Screen {

  public LoadingScreen(ScreenController controller, Node parent, BasicLevel level) {
    super(controller);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }

  private void initializeLayout(BasicLevel level) {

  }

}
