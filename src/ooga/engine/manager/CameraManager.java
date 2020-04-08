package ooga.engine.manager;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {
  Entity mainCharacter;
  int xCenter;
  int yCenter;
  int screenWidth;
  int screenHeight;
  EntityList activatedEntities;
  EntityList deactivatedEntities;
  EntityList onScreenEntities;

  public CameraManager(Entity character){
    mainCharacter = character;
  }

  public void updateCamera(EntityList entities){
    if(mainCharacter.getX()!=xCenter | mainCharacter.getY()!=yCenter){
      double xChange = mainCharacter.getX()-xCenter;
      double yChange = mainCharacter.getY()-yCenter;
      mainCharacter.setX(xCenter);
      mainCharacter.setY(yCenter);
      entities.changeAllCoordinates(xChange, yChange);
      determineEntitiesOnScreen(entities);
    }
  }

  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  public EntityList activateEntities(EntityList entities) {
    for (Entity entity : entities) {
      if (entity.getX() < screenWidth && entity.getY() < screenHeight) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      }
    }
    return activatedEntities;
  }

  private void determineEntitiesOnScreen(EntityList entities) {
    initializeActivationStorage();
    for (Entity entity : entities) {
      if (entity.getX() < screenWidth && entity.getY() < screenHeight && !onScreenEntities
          .contains(entity)) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      } else if ((entity.getX() > screenWidth || entity.getY() > screenHeight) && onScreenEntities
          .contains(entity)) {
        onScreenEntities.removeEntity(entity);
        deactivatedEntities.addEntity(entity);
      }
    }
  }

  public EntityList getActivatedEntities(){
    return activatedEntities;
  }
  public EntityList getDeactivatedEntities(){
    return deactivatedEntities;
  }

}
