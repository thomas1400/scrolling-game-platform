package ooga.engine.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class InputManagerTest extends ApplicationTest {
  private Entity mainEntity;


  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @BeforeEach
  void setUp(){
    mainEntity = EntityBuilder.getEntity("Player");
  }

  @Test
  void handleKeyPress() {
    InputManager im = new InputManager(mainEntity);
    press(KeyCode.RIGHT);
    //im.handleKeyPress(press(KeyCode.RIGHT));
  }

  @Test
  void processInput() {

  }

  @Test
  void handleKeyRelease() {
  }

  @Test
  void invokeMethods() {
  }

  @Test
  void invokeMethod() {
  }
}