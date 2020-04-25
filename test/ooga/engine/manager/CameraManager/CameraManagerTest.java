package ooga.engine.manager.CameraManager;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.BeforeEach;
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
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    entities.addEntity(mainEntity);
    entity = EntityBuilder.getEntity("Brick", "mario");
    entities.addEntity(entity);
  }

  private void setUpCameraManager(String dc, int height, int width){
    cm = new CameraManager(height, width, dc, entities);
    cm.initializeActivationStorage();
    cm.initializeActiveEntities(entities);
  }

  @Test
  void moveRight() {
    setUpCameraManager("right", 400, 600);
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
  void moveDown(){
    setUpCameraManager("down", 600, 600);
    mainEntity.setX(150);
    mainEntity.setY(150);
    entity.setX(100);
    entity.setY(100);
    cm.updateCamera(entities);
    assertEquals(300 - mainEntity.getBoundsInLocal().getHeight() / 2 +.5, mainEntity.getY());
    assertEquals(250 - mainEntity.getBoundsInLocal().getHeight() / 2 + .5, entity.getY());
  }
  @Test
  void moveHorizontal() {
    setUpCameraManager("horizontal", 400, 600);
    mainEntity.setX(150);
    mainEntity.setY(150);
    entity.setX(100);
    entity.setY(100);
    cm.updateCamera(entities);
    assertEquals(400 - mainEntity.getBoundsInLocal().getWidth() / 2, mainEntity.getX());
    assertEquals(350 - mainEntity.getBoundsInLocal().getWidth() / 2, entity.getX());
    assertEquals(150, mainEntity.getY());
    assertEquals(100, entity.getY());
  }
  @Test
  void moveVertical(){
    setUpCameraManager("vertical", 600, 600);
    mainEntity.setX(150);
    mainEntity.setY(150);
    entity.setX(100);
    entity.setY(100);
    cm.updateCamera(entities);
    assertEquals(400 - mainEntity.getBoundsInLocal().getHeight() / 2 +.5, mainEntity.getY());
    assertEquals(350 - mainEntity.getBoundsInLocal().getHeight() / 2 + .5, entity.getY());
  }

  @Test
  void moveCentered(){
    setUpCameraManager("centered", 600, 600);
    mainEntity.setX(150);
    mainEntity.setY(150);
    entity.setX(100);
    entity.setY(100);
    cm.updateCamera(entities);
    assertEquals(300 - mainEntity.getBoundsInLocal().getHeight() / 2 + .5, mainEntity.getY());
    assertEquals(250 - mainEntity.getBoundsInLocal().getHeight() / 2 + .5, entity.getY());
  }

}