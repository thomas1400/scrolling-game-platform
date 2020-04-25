package ooga.model.ability;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class PhysicsTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @Test
  void reflectMethod() {
    Entity myEntity = EntityBuilder.getEntity("Player", "mario");
    Physics myPhysics = new Physics("mario");

    assertEquals(myPhysics.getYVelocity(), 0);
    myPhysics.reflectMethod("jump");
    myPhysics.update(myEntity);
    assertTrue(myPhysics.getYVelocity() < 0);

    assertEquals(myPhysics.getXVelocity(), 0);
    myPhysics.reflectMethod("moveRight");
    myPhysics.update(myEntity);
    assertTrue(myPhysics.getXVelocity() > 0);

    myPhysics.reflectMethod("supportX");
    myPhysics.update(myEntity);
    assertEquals(myPhysics.getXVelocity(), 0);

    assertTrue(myPhysics.getYVelocity() < 0);
    myPhysics.reflectMethod("bounceY");
    myPhysics.update(myEntity);
    assertTrue(myPhysics.getYVelocity() > 0);
  }
}