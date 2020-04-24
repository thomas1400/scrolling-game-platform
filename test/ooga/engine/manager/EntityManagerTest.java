package ooga.engine.manager;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;
import ooga.utility.event.CollisionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class EntityManagerTest extends ApplicationTest {
  private Entity mainEntity;
  private Entity entity;
  private EntityList entities;
  private EntityManager entityManager;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
  }

  @BeforeEach
  void setUp(){
    entities = new EntityList();
    mainEntity = EntityBuilder.getEntity("Player", "mario");
    entities.addEntity(mainEntity);
    entity = EntityBuilder.getEntity("Brick", "mario");
    entities.addEntity(entity);
    entityManager = new EntityManager(entities);
    entityManager.initializeEntityLists();

  }

  @Test
  void initializeEntityLists() {
    entityManager.initializeEntityLists();
    assertEquals(0, entityManager.getAddedEntities().size());
    assertEquals(0, entityManager.getRemovedEntities().size());
  }

  private Entity createNewEntity(){
    return EntityBuilder.getEntity("Goomba", "mario");
  }

  private EntityList createNewEntityListWithEntity(Entity entity){
    EntityList newEntities = new EntityList();
    newEntities.addEntity(entity);
    return newEntities;
  }

  @Test
  void addNewEntity() {
    EntityList entitiesReceived = new EntityList();
    entitiesReceived.addAllEntities(entities);
    Entity newEntity = createNewEntity();
    entitiesReceived.addEntity(newEntity);
    entityManager.manageEntitiesFromCollisions(entitiesReceived);
    assertTrue(entityManager.getAddedEntities().contains(newEntity));
    assertTrue(entityManager.getEntities().contains(newEntity));
  }

  @Test
  void mainEntityDies(){
    CollisionEvent sideDamage = new CollisionEvent("Side", "Damage", EntityBuilder.getEntity("Goomba", "mario"));
    mainEntity.handleCollision(sideDamage);
    assertTrue(mainEntity.isDead());
    entityManager.manageEntitiesFromCollisions(entities);
    assertFalse(entityManager.getRemovedEntities().contains(mainEntity));
    assertTrue(entityManager.getEntities().contains(mainEntity));
  }

  @Test
  void entityDies(){
    CollisionEvent bottomDamage = new CollisionEvent("Bottom", "Break", EntityBuilder.getEntity("Player", "mario"));
    entity.handleCollision(bottomDamage);
    assertTrue(entity.isDead());
    entityManager.manageEntitiesFromCollisions(entities);
    assertTrue(entityManager.getRemovedEntities().contains(entity));
    assertFalse(entityManager.getEntities().contains(entity));
  }

  @Test
  void getEntities() {
    assertEquals(entities, entityManager.getEntities());
  }

  @Test
  void addNewEntities() {
    Entity goomba = createNewEntity();
    EntityList newEntities = createNewEntityListWithEntity(goomba);
    entityManager.entityMovedOnScreen(newEntities);
    assertTrue(entityManager.getAddedEntities().contains(goomba));
  }

  @Test
  void removeOldEntities() {
    EntityList newEntities = createNewEntityListWithEntity(entity);
    entityManager.entityMovedOffScreen(newEntities);
    assertTrue(entityManager.getRemovedEntities().contains(entity));
  }

  @Test
  void addEntity() {
    Entity goomba = createNewEntity();
    entityManager.addEntity(goomba);
    assertTrue(entityManager.getAddedEntities().contains(goomba));
    assertTrue(entityManager.getEntities().contains(goomba));
  }

  @Test
  void removeEntity() {
    entityManager.removeEntity(entity);
    assertTrue(entityManager.getRemovedEntities().contains(entity));
    assertFalse(entityManager.getEntities().contains(entity));
  }

  @Test
  void addAllEntities() {
    Entity goomba = createNewEntity();
    EntityList newEntities = createNewEntityListWithEntity(goomba);
    entityManager.addAllEntities(newEntities);
    assertTrue(entityManager.getAddedEntities().contains(goomba));
    assertTrue(entityManager.getEntities().contains(goomba));
  }

  @Test
  void removeAllEntities() {
    EntityList newEntities = createNewEntityListWithEntity(entity);
    entityManager.removeAllEntities(newEntities);
    assertTrue(entityManager.getRemovedEntities().contains(entity));
    assertFalse(entityManager.getEntities().contains(entity));
  }
}