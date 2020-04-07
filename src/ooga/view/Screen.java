package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Screen extends Pane implements Displayable {

  private static final int PREF_WIDTH = 800, PREF_HEIGHT = 600;
  static final String FONT_FAMILY = "Cambria";
  static final double PADDING = 5.0;

  private static final String RESOURCES_PATH = "ooga.view.resources.";
  private static final String BUTTON_TRANS_SUFFIX = "ButtonTransitions";

  protected double workingHeight, workingWidth;
  protected ResourceBundle buttonTransitions;

  public Screen() {
    initializeButtonTransitions();
    initializeScreen();
  }

  protected void initializeButtonTransitions() {
    buttonTransitions = ResourceBundle.getBundle(
        RESOURCES_PATH + this.getClass().getSimpleName() + BUTTON_TRANS_SUFFIX
    );
  }

  protected void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  protected void setWorkingDimensions(int vPanels, int hPanels) {
    workingHeight = this.getPrefHeight() - (vPanels+1) * PADDING;
    workingWidth = this.getPrefWidth() - (hPanels+1) * PADDING;
  }

  @Override
  public Node getDisplay() {
    return this;
  }

}
