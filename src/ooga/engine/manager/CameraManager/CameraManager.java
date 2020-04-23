package ooga.engine.manager.CameraManager;

import java.util.ResourceBundle;
import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {

  private Entity mainEntity;
  private double screenHeight;
  private double screenWidth;
  private EntityList activatedEntities;
  private EntityList deactivatedEntities;
  private EntityList onScreenEntities;
  private static final String directionControllerResources = "directioncontrollers/directioncontrollers";
  private DirectionController myDirectionController;

  public CameraManager(Entity character, double height, double width, String direction, EntityList entities) {
    mainEntity = character;
    ResourceBundle myDirectionControllerResources = ResourceBundle
        .getBundle(directionControllerResources);
    screenHeight = height;
    screenWidth = width;
    String directionType = myDirectionControllerResources.getString(direction);
    try {
      myDirectionController = (DirectionController) Class
          .forName("ooga.engine.manager.CameraManager.DirectionControllers." + directionType).newInstance();
      myDirectionController.initialize(entities, height, width, character);
    } catch (InstantiationException e) {
      //FIXME
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      //FIXME
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      //FIXME
      e.printStackTrace();
    }
  }

  public void updateCamera(EntityList entities ){
    //System.out.println(mainEntity.getX() + " " + mainEntity.getY());
    myDirectionController.updateCameraPosition(entities, mainEntity);
    determineEntitiesOnScreen(entities);
    determinedead();
  }


  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  public EntityList initializeActiveEntities(EntityList entities) {
    initializeActivationStorage();
    myDirectionController.updateCameraPosition(entities, mainEntity);
    onScreenEntities = new EntityList();
    for (Entity entity : entities) {
      if (entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX()< screenWidth && entity.getBoundsInLocal().getMaxY() < screenHeight) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      }
    }
    return activatedEntities;
  }

  protected void determineEntitiesOnScreen(EntityList entities) {
    initializeActivationStorage();
    for (Entity entity : entities) {
      if (entityIsOnScreen(entity) && !onScreenEntities.contains(entity)) {
        activatedEntities.addEntity(entity);
        onScreenEntities.addEntity(entity);
      } else if ((!entityIsOnScreen(entity)) && onScreenEntities.contains(entity)) {
        onScreenEntities.removeEntity(entity);
        deactivatedEntities.addEntity(entity);
      }
    }
  }

  public void determinedead(){
    EntityList entitiesToRemove = new EntityList();
    for (Entity entity : onScreenEntities) {
      if (entity.isDead()) {
        entitiesToRemove.addEntity(entity);
      }
    }
    onScreenEntities.removeAllEntities(entitiesToRemove);
    //removeAllEntities(entitiesToRemove);
  }

  private boolean entityIsOnScreen(Entity entity){
    //return entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMaxY()< screenHeight;
    return !entity.isDead() && entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMinY()< screenHeight;
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
