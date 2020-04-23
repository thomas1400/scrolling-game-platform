package ooga.engine.manager;

import ooga.controller.Communicable;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class EntityManager implements Communicable {

  private EntityList myEntityList;
  private EntityList addedEntities;
  private EntityList removedEntities;
  private Entity myMainEntity;

  public EntityManager(EntityList entities) {
    myEntityList = entities;
    myMainEntity = entities.getMainEntity();
    initializeEntityLists();
  }

  public void initializeEntityLists() {
    addedEntities = new EntityList();
    removedEntities = new EntityList();
    addedEntities.clear();
    removedEntities.clear();
  }

  public void manageEntitiesFromCollisions(EntityList entitiesReceived) {
    for (Entity entity : entitiesReceived) {
      if (!myEntityList.contains(entity)) {
        addEntity(entity);
      }
    }
    removeDeadEntities();
  }

  private void removeDeadEntities() {
    EntityList entitiesToRemove = new EntityList();
    for (Entity entity : myEntityList) {
      if (entity.isDead() && !entity.equals(myMainEntity)) {
        entitiesToRemove.addEntity(entity);
      }
    }
    System.out.println("main" + myEntityList.contains(myMainEntity));
    System.out.println("dead" + myMainEntity.isDead());
    removeAllEntities(entitiesToRemove);
  }

  public EntityList getEntities(){
    return myEntityList;
  }

  public EntityList getAddedEntities(){
    return addedEntities;
  }

  public EntityList getRemovedEntities() {
    return removedEntities;
  }

  public void entityMovedOnScreen(EntityList entities) {
    addedEntities.addAllEntities(entities);
  }

  public void entityMovedOffScreen(EntityList entities) {
    removedEntities.addAllEntities(entities);
  }

  @Override
  public void addEntity(Entity entity) {
    addedEntities.addEntity(entity);
    myEntityList.addEntity(entity);
  }

  @Override
  public void removeEntity(Entity entity) {
    removedEntities.addEntity(entity);
    myEntityList.removeEntity(entity);
  }

  @Override
  public void addAllEntities(EntityList entities) {
    addedEntities.addAllEntities(entities);
    myEntityList.addAllEntities(entities);
  }

  @Override
  public void removeAllEntities(EntityList entities) {
    removedEntities.addAllEntities(entities);
    myEntityList.removeAllEntities(entities);
  }
}
