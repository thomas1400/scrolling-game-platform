package ooga.engine.manager;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {

  Entity mainEntity;
  double xCenter;
  double screenHeight;
  double screenWidth;
  EntityList activatedEntities;
  EntityList deactivatedEntities;
  EntityList onScreenEntities;

  public CameraManager(Entity character, double height, double width) {
    mainEntity = character;
    screenHeight = height;
    screenWidth = width +10;
    xCenter = screenWidth/2- mainEntity.getBoundsInLocal().getWidth()/2;
  }

  public void updateCamera(EntityList entities) {
    if (mainEntity.getX()>xCenter) {
      double xChange = mainEntity.getX()- xCenter;
      resetMainEntityToCenter();
      entities.changeAllXCoordinates(xChange);
      determineEntitiesOnScreen(entities);
    }
  }

  private void resetMainEntityToCenter() {
    mainEntity.setX(xCenter);
  }

  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  public EntityList initializeActiveEntities(EntityList entities) {
    initializeActivationStorage();
    onScreenEntities = new EntityList();
    for (Entity entity : entities) {
      if (entity.getX()> 10 && entity.getBoundsInLocal().getMaxX()< screenWidth && entity.getY() < screenHeight) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      }
    }
    return activatedEntities;
  }

  private void determineEntitiesOnScreen(EntityList entities) {
    initializeActivationStorage();
    for (Entity entity : entities) {
      if (entity.getX()> 10 && entity.getBoundsInLocal().getMaxX() < screenWidth && entity.getY() < screenHeight && !onScreenEntities
          .contains(entity)) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      } else if ((entity.getBoundsInLocal().getMaxX() > screenWidth || entity.getX()< 10 || entity.getY() > screenHeight) && onScreenEntities
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
  public EntityList getOnScreenEntities(){
    return onScreenEntities;
  }

}
