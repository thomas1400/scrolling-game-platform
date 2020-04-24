package ooga.engine.manager.CameraManager;

import java.util.ResourceBundle;
import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * This class is used to update the positions of the entities on screen based upon the directionController object and the position of the main entity
 * This class uses composition in that the type of directioncontroller can be changed and is an instance variable
 * This class assumes that there is a main entity that the position of other entities are based off of
 * @author Cayla Schuval
 */
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

  /**
   * @param height double containing the height of the screen
   * @param width double containing the width of the screen
   * @param direction String containing the type of direction controller
   * @param entities EntityList containing the initial entities in the game
   */
  public CameraManager(double height, double width, String direction, EntityList entities) {
    Entity mainEntity = entities.getMainEntity();
    ResourceBundle myDirectionControllerResources = ResourceBundle.getBundle(directionControllerResources);
    screenHeight = height;
    screenWidth = width;
    String directionType = myDirectionControllerResources.getString(direction);
    try {
      myDirectionController = (DirectionController) Class.forName(directionControllerOptionsLocation + directionType).newInstance();
      myDirectionController.initialize(entities, height, width);
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, ERROR_MESSAGE);
    }
  }

  /**
   * @param entities EntityList of all the entities in the game
   * updates the position of all of the entities based upon the direction controller type and the location of the main entity
   * determines which entities are located on the screen based upon the updated position
   */
  public void updateCamera(EntityList entities ){
    myDirectionController.updateCameraPosition(entities, screenHeight, screenWidth);
    determineEntitiesOnScreen(entities);
  }

  /**
   * initializes EntityLists after each game tick so that only the change in entities present on the screen is stored
   */
  public void initializeActivationStorage() {
    activatedEntities = new EntityList();
    deactivatedEntities = new EntityList();
  }

  /**
   * @param entities EntityList containing the initial entities in the game
   * called by the LevelLoop to determine which entities start on the screen so that they can be sent to the levelcontroller
   * establishes framework of the initial set of entities on the screen so that the loop only needs to keep track of the entities that have moved on or off screen
   */
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

  private void determineEntitiesOnScreen(EntityList entities) {
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

  private void removeDeadEntities(){
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

  /**
   * @return EntityList containing all of the entities that have moved on the screen (been activated) in this game tick
   */
  public EntityList getActivatedEntities(){
    return activatedEntities;
  }
  /**
   * @return EntityList containing all of the entities that have moved off of the screen (been deactivated) in this game tick
   */
  public EntityList getDeactivatedEntities(){
    return deactivatedEntities;
  }
  /**
   * @return EntityList containing all of the entities that are currently on the screen
   */
  public EntityList getOnScreenEntities(){
    return onScreenEntities;
  }

}
