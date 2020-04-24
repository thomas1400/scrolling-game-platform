package ooga.engine.manager;

import javafx.scene.input.KeyCode;
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
    mainEntity = EntityBuilder.getEntity("Player", "mario");
  }

  @Test
  void handleKeyPress() {
    InputManager im = new InputManager(mainEntity, "mario");
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