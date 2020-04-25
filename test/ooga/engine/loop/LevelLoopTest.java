package ooga.engine.loop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;
import javafx.stage.Stage;
import ooga.controller.LevelBuilder;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class LevelLoopTest extends ApplicationTest {
  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private LevelLoop loop;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @BeforeEach
  void setUp () {
    entities = new EntityList();
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    mainEntity.setX(100);
    mainEntity.setY(100);
    entities.addEntity(mainEntity);
    entity = EntityBuilder.getEntity("Brick", "mario");
    entity.setX(75);
    entity.setY(75);
    entities.addEntity(entity);
    //loop = new Level()
  }


  @Test
  void handleKeyPressed() {
  }

  @Test
  void handleKeyReleased() {
  }

  @Test
  void begin() {
  }

  @Test
  void end() {
  }

  @Test
  void pause() {
  }

  @Test
  void resume() {
  }

  @Test
  void getInitialVisibleEntityList() {
  }
}