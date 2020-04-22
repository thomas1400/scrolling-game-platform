package ooga.engine.manager;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class CollisionManagerTest extends ApplicationTest {
  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private EntityManager entityManager;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @Test
  void manageCollisions() {
  }

  @Test
  void getEntitiesReceived() {
  }
}