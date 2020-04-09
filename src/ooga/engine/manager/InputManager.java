package ooga.engine.manager;

import java.util.List;
import java.util.Observer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ooga.model.entity.Entity;

public class InputManager {
  Entity myMainEntity;

  private List<Observer> observers;

  public InputManager(Entity mainEntity){
    myMainEntity = mainEntity;
  }

  public void handleKeyInput(KeyEvent keyEvent) {
    //myMainEntity.receiveUserInput(keyEvent);
  }
}