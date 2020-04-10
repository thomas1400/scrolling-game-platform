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
<<<<<<< HEAD
    if (keyEvent.getCode() == KeyCode.RIGHT){
      myMainEntity.setX(myMainEntity.getX()+10);
      //myMainEntity.moveRight();
    }
    if (keyEvent.getCode() == KeyCode.LEFT){
      myMainEntity.setY(myMainEntity.getY()-10);
      //myMainEntity.moveLeft();
    }
    if (keyEvent.getCode() == KeyCode.UP){
      //myMainEntity.jump();
    }

=======
    //myMainEntity.receiveUserInput(keyEvent);
>>>>>>> d552f8bb7989c0bc8bca58734e024d8ffdc2144e
  }
}