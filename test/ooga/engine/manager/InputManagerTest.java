package ooga.engine.manager;

import static org.junit.jupiter.api.Assertions.*;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class InputManagerTest extends ApplicationTest {
  private KeyEvent keyEvent;
  private InputManager inputManager;
  private static final String GAME_TYPE = "mario";
  private Entity mainEntity;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    inputManager = new InputManager(mainEntity, GAME_TYPE);
  }

  @Test
  void handleKeyPress() {
    inputManager.handleKeyPress("RIGHT");
    assertTrue(inputManager.getKeysCurrentlyPressed().contains("RIGHT"));
  }

  @Test
  void handleKeyRelease() {
    handleKeyPress();
    inputManager.handleKeyRelease("RIGHT");
    assertFalse(inputManager.getKeysCurrentlyPressed().contains("RIGHT"));
  }

}