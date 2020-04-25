package ooga.controller;

import javafx.scene.input.KeyEvent;

public interface Handleable {

  void handleKeyPressed(KeyEvent keyEvent);

  void handleKeyReleased(KeyEvent keyEvent);
}
