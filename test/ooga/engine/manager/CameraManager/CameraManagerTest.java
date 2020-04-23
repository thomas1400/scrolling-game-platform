package ooga.engine.manager.CameraManager;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Camera;
import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class CameraManagerTest extends ApplicationTest {

  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private CameraManager cm;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }



  @BeforeEach
  void setUp () {
    entities = new EntityList();
    mainEntity = EntityBuilder.getEntity("Player");
    entities.addEntity(mainEntity);
    entity = EntityBuilder.getEntity("Brick");
    entities.addEntity(entity);
    cm = new CameraManager(mainEntity,400, 600, "right", entities);
    cm.initializeActivationStorage();
    cm.initializeActiveEntities(entities);
  }

    @Test
  void updateCamera() {
    mainEntity.setX(150);
    mainEntity.setY(150);
    entity.setX(100);
    entity.setY(100);
    cm.updateCamera(entities);
    assertEquals(300 - mainEntity.getBoundsInLocal().getWidth() / 2, mainEntity.getX());
    assertEquals(250 - mainEntity.getBoundsInLocal().getWidth() / 2, entity.getX());
    assertEquals(150, mainEntity.getY());
    assertEquals(100, entity.getY());
  }

  @Test
  void initializeActivationStorage() {
    cm.initializeActivationStorage();
    assertEquals(0, cm.getActivatedEntities().size());
    assertEquals(0, cm.getDeactivatedEntities().size());
  }

  @Test
  void initializeActiveEntities() {
    mainEntity.setX(300);
    mainEntity.setY(50);
    entity.setX(100);
    entity.setY(100);
    EntityList activated= cm.initializeActiveEntities(entities);
    assertTrue(activated.contains(mainEntity));
    assertFalse(activated.contains(entity));
  }

  @Test
  void determineEntitiesOnScreen() {

  }

  @Test
  void determinedead() {
  }

  @Test
  void getActivatedEntities() {
  }

  @Test
  void getDeactivatedEntities() {
  }

  @Test
  void getOnScreenEntities() {
  }
}