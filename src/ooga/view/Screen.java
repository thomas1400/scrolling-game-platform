package ooga.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Screen extends Pane implements Displayable {

  private static final int PREF_WIDTH = 800, PREF_HEIGHT = 600;
  static final String FONT_FAMILY = "Cambria";

  protected void initializeScreen() {
    this.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
  }

  @Override
  public Node getDisplay() {
    return this;
  }

}
