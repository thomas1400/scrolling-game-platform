package ooga.engine.manager.CameraManager;

import java.util.ResourceBundle;
import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {

  private double screenHeight;
  private double screenWidth;
  private EntityList activatedEntities;
  private EntityList deactivatedEntities;
  private EntityList onScreenEntities;
  private static final String directionControllerResources = "directioncontrollers"
      + "/directioncontrollers";
  private DirectionController myDirectionController;
  private static final String directionControllerOptionsLocation =  "ooga.engine.manager.CameraManager.DirectionControllers.";
  private static final String ERROR_MESSAGE = "No DirectionController associated with this scroll type";

  public CameraManager(double height, double width, String direction,
      EntityList entities) {
    Entity mainEntity = entities.getMainEntity();
    ResourceBundle myDirectionControllerResources = ResourceBundle.getBundle(directionControllerResources);
    screenHeight = height;
    screenWidth = width;
    String directionType = myDirectionControllerResources.getString(direction);
    try {
      myDirectionController = (DirectionController) Class.forName(directionControllerOptionsLocation + directionType).newInstance();
      myDirectionController.initialize(entities, height, width, mainEntity);
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, ERROR_MESSAGE);
    }
  }

  public void updateCamera(EntityList entities ){
    myDirectionController.updateCameraPosition(entities, screenHeight, screenWidth);
    determineEntitiesOnScreen(entities);
  }


  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  public EntityList initializeActiveEntities(EntityList entities) {
    initializeActivationStorage();
    myDirectionController.updateCameraPosition(entities, screenHeight, screenWidth);
    onScreenEntities = new EntityList();
    for (Entity entity : entities) {
      if (entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX()< screenWidth && entity.getBoundsInLocal().getMinY() < screenHeight) {
        newEntityMovesOnScreen(entity);
      }
    }
    return activatedEntities;
  }

  private void newEntityMovesOnScreen(Entity entity) {
    activatedEntities.addEntity(entity);
    onScreenEntities.addEntity(entity);
  }

  protected void determineEntitiesOnScreen(EntityList entities) {
    initializeActivationStorage();
    removeDeadEntities();
    for (Entity entity : entities) {
      if (entityIsOnScreen(entity) && !onScreenEntities.contains(entity)) {
        newEntityMovesOnScreen(entity);
      } else if ((!entityIsOnScreen(entity)) && onScreenEntities.contains(entity)) {
        entityMovesOffScreen(entity);
      }
    }
  }

  public void removeDeadEntities(){
    EntityList entitiesToRemove = new EntityList();
    for (Entity entity : onScreenEntities) {
      if (entity.isDead()) {
        entitiesToRemove.addEntity(entity);
      }
    }
    onScreenEntities.removeAllEntities(entitiesToRemove);
  }

  private boolean entityIsOnScreen(Entity entity){
    return entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMinY()< screenHeight;}

  private void entityMovesOffScreen(Entity entity) {
    onScreenEntities.removeEntity(entity);
    deactivatedEntities.addEntity(entity);
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
