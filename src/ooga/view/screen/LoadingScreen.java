package ooga.view.screen;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ooga.controller.levels.BasicLevel;

public class LoadingScreen extends Screen {

  private static final Color BACKGROUND = Color.BLACK;
  private static final Color TEXT_COLOR = Color.WHITE;
  private static final double PADDING = 10.0;
  private static final int FONT_SIZE = 25;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String RESOURCES_SUFFIX = "Text";

  public LoadingScreen(Node parent, BasicLevel level) {
    super(null);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());

    this.getStyleClass().add("loading-screen");

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }

  private void initializeLayout(BasicLevel level) {

  }

}
