package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ooga.controller.ScreenController;
import ooga.controller.levels.BasicLevel;

public class DeathScreen extends Screen {

  public DeathScreen(ScreenController controller, Node parent, int livesRemaining) {
    super(controller);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("lives-label", new Label(getResource("lives") + " " + livesRemaining));

    loadLayout();
  }

  private void initializeLayout(BasicLevel level) {

  }

}
