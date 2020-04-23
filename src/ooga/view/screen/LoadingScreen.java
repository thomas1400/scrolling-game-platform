package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import ooga.controller.levels.BasicLevel;

public class LoadingScreen extends Screen {

  public LoadingScreen(Node parent, BasicLevel level) {
    super(null);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }


}
