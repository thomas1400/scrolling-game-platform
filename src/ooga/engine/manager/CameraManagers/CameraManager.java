package ooga.engine.manager.CameraManagers;

import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CameraManager {

  protected Entity mainEntity;
  protected double screenHeight;
  protected double screenWidth;
  protected double change;
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
          .forName("ooga.engine.manager.CameraManagers." + directionType).newInstance();
      //myDirectionController.setToCenter(entities, screenHeight, screenWidth, mainEntity);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateCamera(EntityList entities ){
    myDirectionController.updateCameraPosition(entities,screenHeight, screenWidth, mainEntity);
    determineEntitiesOnScreen(entities);
  }


  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  public EntityList initializeActiveEntities(EntityList entities) {
    initializeActivationStorage();
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

  private boolean entityIsOnScreen(Entity entity){
    //return entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMaxY()< screenHeight;
    return entity.getBoundsInLocal().getMaxX()> 0 && entity.getBoundsInLocal().getMinX() < screenWidth && entity.getBoundsInLocal().getMinY() > 0 && entity.getBoundsInLocal().getMaxY()< screenHeight;
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
