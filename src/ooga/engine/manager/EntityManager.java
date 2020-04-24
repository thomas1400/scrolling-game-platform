package ooga.engine.manager;

import ooga.controller.Communicable;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * This class is used to store all of the entities in the game, entities that are added with each game tick, and entities that are removed with each game tick
 * Dependends on other management classes to provide entities that have been added or removed
 * @author Cayla Schuval
 */
public class EntityManager implements Communicable {

  private EntityList myEntityList;
  private EntityList addedEntities;
  private EntityList removedEntities;
  private Entity myMainEntity;

  /**
   * @param entities EntityList contains the entities that the game contains at the start of the game
   * Determines what the main entity is
   * initializes EntityLists to be used to store added and removed entities
   */
  public EntityManager(EntityList entities) {
    myEntityList = entities;
    myMainEntity = entities.getMainEntity();
    initializeEntityLists();
  }

  /**
   * initializes EntityLists to be used to store added and removed entities
   * Ensures that the EntityLists are cleared
   */
  public void initializeEntityLists() {
    addedEntities = new EntityList();
    removedEntities = new EntityList();
    addedEntities.clear();
    removedEntities.clear();
  }

  /**
   * Determines if the entities received from collisions have resulted in any new or dead entities
   * @param entitiesReceived EntityList of entities received from collisions
   */
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
    removeAllEntities(entitiesToRemove);
  }

  /**
   * @return EntityList containing the current entities in the entire game
   */
  public EntityList getEntities(){
    return myEntityList;
  }

  /**
   * @return EntityList containing the entities that were added in this game tick
   */
  public EntityList getAddedEntities(){
    return addedEntities;
  }

  /**
   * @return EntityList containing the entities that were removed in this game tick
   */
  public EntityList getRemovedEntities() {
    return removedEntities;
  }

  /**
   * @param entities EntityList containing the entities that moved on to the screen, but were previously created
   * Adds these entities to the addedEntities EntityList
   * These entities are not added to the main EntityList because they were created when the initial game was created
   */
  public void entityMovedOnScreen(EntityList entities) {
    addedEntities.addAllEntities(entities);
  }

  /**
   * @param entities EntityList containing the entities that moved off the screen, but did not die
   * Adds these entities to the removedEntities EntityList
   * These entities are not removed from the main EntityList because they did not die
   */
  public void entityMovedOffScreen(EntityList entities) {
    removedEntities.addAllEntities(entities);
  }

  /**
   * @param entity Entity object that has been created and needs to be added to the screen
   * Adds entity to the addedEntities EntityList so that it will be sent to the LevelController
   * Adds entity to the main EntityList so that they can be use to detect collisions and move with the camera manager
   */
  public void addEntity(Entity entity) {
    addedEntities.addEntity(entity);
    myEntityList.addEntity(entity);
  }

  /**
   * @param entity Entity object that has died and needs to be removed from te screen
   * Adds entity to the removedEntities EntityList so that it will be sent to the LevelController to remove from the screen
   * Removes entity from the main EntityList so that they will not be used to detect collisions nor to move with the camera manager
   */
  public void removeEntity(Entity entity) {
    removedEntities.addEntity(entity);
    myEntityList.removeEntity(entity);
  }


  /**
   * @param entities EntityList containing entities that have been created and need to be added to the screen
   * Adds entities to the addedEntities EntityList so that it will be sent to the LevelController
   * Adds entityies to the main EntityList so that they can be use to detect collisions and move with the camera manager
   */public void addAllEntities(EntityList entities) {
    addedEntities.addAllEntities(entities);
    myEntityList.addAllEntities(entities);
  }

  /**
   * @param entities EntityList containing entities that have died and need to be removed from te screen
   * Adds entities to the removedEntities EntityList so that it will be sent to the LevelController to remove from the screen
   * Removes entities from the main EntityList so that they will not be used to detect collisions nor to move with the camera manager
   */
  public void removeAllEntities(EntityList entities) {
    removedEntities.addAllEntities(entities);
    myEntityList.removeAllEntities(entities);
  }
}
