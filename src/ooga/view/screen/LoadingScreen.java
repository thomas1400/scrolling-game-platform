package ooga.view.screen;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ooga.controller.data.BasicLevel;
import ooga.view.factory.ControlFactory;
import ooga.view.fxlr.FXLRParser;

public class LoadingPane extends Screen {

  private static final Color BACKGROUND = Color.BLACK;
  private static final Color TEXT_COLOR = Color.WHITE;
  private static final double PADDING = 10.0;
  private static final int FONT_SIZE = 25;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String RESOURCES_SUFFIX = "Text";

  public LoadingPane(Node parent, BasicLevel level) {
    super(null);
    this.setPrefSize(parent.getLayoutBounds().getWidth(), parent.getLayoutBounds().getHeight());

    dynamicNodes.put("level-label", new Label(level.getMainTitle()));

    loadLayout();
  }

  private void initializeLayout(BasicLevel level) {

  }

}
