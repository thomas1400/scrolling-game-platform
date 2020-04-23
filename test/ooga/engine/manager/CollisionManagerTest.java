package ooga.engine.manager;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.engine.manager.CameraManager.CameraManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class CollisionManagerTest extends ApplicationTest {

  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private CollisionManager collisionManager;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @BeforeEach
  void setUp() {
    entities = new EntityList();
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    entities.addEntity(mainEntity);
    entity = EntityBuilder.getEntity("Brick", "mario");
    entities.addEntity(entity);
    collisionManager = new CollisionManager();
  }

  private void setPosition(double X1, double X2, double Y1, double Y2) {
    mainEntity.setX(X1);
    entity.setX(X2);
    mainEntity.setY(Y1);
    entity.setY(Y2);
  }

  @Test
  void minXMaxXCollision() {
    setPosition(100.10 + entity.getBoundsInLocal().getWidth(), 100.15, 101, 100);
    collisionManager.manageCollisions(entities);
    assertEquals("minXmaxX", collisionManager.getMin());
  }

  @Test
  void maxXMinXCollision() {
    setPosition(20.15, 20.10 + mainEntity.getBoundsInLocal().getWidth(), 101, 100);
    collisionManager.manageCollisions(entities);
    assertEquals("maxXminX", collisionManager.getMin());
  }

  @Test
  void maxYMinYCollision() {
    setPosition(10, 8, 50.5, 50.45 + mainEntity.getBoundsInLocal().getHeight());
    collisionManager.manageCollisions(entities);
    assertEquals("maxYminY", collisionManager.getMin());
  }

  @Test
  void minYMaxYCollision() {
    setPosition(10, 8, 500.5 + entity.getBoundsInLocal().getHeight(), 500.55);
    collisionManager.manageCollisions(entities);
    assertEquals("minYmaxY", collisionManager.getMin());
  }

  @Test
  void getEntitiesReceived() {
    minYMaxYCollision();
    EntityList received = collisionManager.getEntitiesReceived();
    assertTrue(received.contains(mainEntity));
    assertTrue(received.contains(entity));
  }

  @Test
  void checkVerticalResults() {
    minYMaxYCollision();
    String[] results = collisionManager.getResults();
    assertEquals("Top", results[0]);
    assertEquals("Bottom", results[1]);
    maxYMinYCollision();
    String[] results2 = collisionManager.getResults();
    assertEquals("Bottom", results2[0]);
    assertEquals("Top", results2[1]);
  }
  @Test
  void checkHorizontalResults() {
    minXMaxXCollision();
    String[] results = collisionManager.getResults();
    assertEquals("Side", results[0]);
    assertEquals("Side", results[1]);
    maxXMinXCollision();
    String[] results2 = collisionManager.getResults();
    assertEquals("Side", results2[0]);
    assertEquals("Side", results2[1]);
  }

  @Test
  void entityCollidesWithSelf(){
    entities.removeEntity(entity);
    collisionManager.manageCollisions(entities);
    assertEquals(0, collisionManager.getEntitiesReceived().size());
  }
  @Test
  void entityCollidesWithSameObject(){
    entities.addEntity(entity);
    minYMaxYCollision();
    assertEquals(2, collisionManager.getEntitiesReceived().size());
  }
}