package ooga.view;

import javafx.scene.Node;

public abstract class Screen extends Node implements Displayable {

  @Override
  public Node getDisplay() {
    return this;
  }

}
