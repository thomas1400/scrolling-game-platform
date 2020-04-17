package ooga.engine.manager;

import ooga.controller.Communicable;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class EntityManager implements Communicable {

  //Entity entityReceived;
  private EntityList myEntityList;
  private EntityList addedEntities;
  private EntityList removedEntities;
  private CollisionManager myCollisionManager;

  public EntityManager(EntityList entities) {
    myEntityList = entities;
    initializeEntityLists();
  }

  public void initializeEntityLists() {
    addedEntities = new EntityList();
    removedEntities = new EntityList();
  }

  public void manageEntities(EntityList entitiesReceived) {
    for (Entity entity : entitiesReceived) {
      if (!myEntityList.contains(entity)) {
        addEntity(entity);
      }
    }
    checkForDeadEntities();
  }

  private void checkForDeadEntities() {
    EntityList entitiesToRemove = new EntityList();
    for (Entity entity : myEntityList) {
      if (entity.isDead()) {
        entitiesToRemove.addEntity(entity);
      }
    }
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

  public void addNewEntities(EntityList entities) {
    addedEntities.addAllEntities(entities);
    //needs to be reset somewhere
  }

  public void removeOldEntities(EntityList entities) {
    removedEntities.addAllEntities(entities);
    //needs to be reset somewhere
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
    //needs to be reset somewhere
  }

  @Override
  public void removeAllEntities(EntityList entities) {
    removedEntities.addAllEntities(entities);
    myEntityList.removeAllEntities(entities);
    // needs to be reset somewhere
  }
}
