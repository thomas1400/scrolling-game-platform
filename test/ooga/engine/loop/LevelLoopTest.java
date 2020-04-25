package ooga.engine.loop;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.logging.Level;
import javafx.stage.Stage;
import ooga.controller.LevelBuilder;
import ooga.controller.LevelController;
import ooga.controller.levels.CompleteLevel;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.engine.manager.CollisionManager;
import ooga.engine.manager.EntityManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class LevelLoopTest extends ApplicationTest {
  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private CollisionManager collisionManager;
  private Entity entity2;
  private EntityManager entityManager;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @BeforeEach
  void setUp () {
    entities = new EntityList();
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    entities.addEntity(mainEntity);
    entities.setMainEntity(mainEntity);
    entity2 = EntityBuilder.getEntity("Player", "mario");
    entity = EntityBuilder.getEntity("Goomba", "mario");
    entities.addEntity(entity);
    entities.addEntity(entity2);
    collisionManager = new CollisionManager(mainEntity);

  }

  private void setPosition(double X1, double X2, double Y1, double Y2) {
    entity2.setX(X1);
    entity.setX(X2);
    entity2.setY(Y1);
    entity.setY(Y2);
  }


  @Test
  void minXMaxXCollision() {
    setPosition(100.10 + entity.getBoundsInLocal().getWidth(), 100.15, 101, 100);
    collisionManager.manageCollisions(entities);
    assertTrue(entity2.isDead());
    //assertEquals(2, collisionManager.getEntitiesReceived().size());

  }

  @Test
  void entitiesFromCollisions(){
    //minXMaxXCollision();
    entityManager.manageEntitiesFromCollisions(collisionManager.getEntitiesReceived());
    assertTrue(entityManager.getRemovedEntities().contains(entity2));
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