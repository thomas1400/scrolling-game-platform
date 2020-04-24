package ooga.engine.manager.CameraManager.DirectionControllers.CenteredDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Subclass of Direction Controller
 * specifies movement of entity so that screen moves as the main entity moves up, down, right, and left
 * @author Cayla Schuval
 */
public class CenteredDirectionController extends DirectionController {

  private double xCenter;
  private double yCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double xChange;
  private double yChange;
  Entity mainEntity;

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param myScreenHeight screen height of the game used to determine if entities move on or off screen
   * @param myScreenWidth screen width of the game used to determine if entities move on or off screen
   * checks the X and Y positions of the main entity and updates all entity positions respectively
   */
  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getX() != xCenter | mainEntity.getY() != yCenter){
      setToCenter(entities);
    }
  }

  private void setToCenter(EntityList entities) {
    xChange = mainEntity.getX() - xCenter;
    yChange = mainEntity.getY() - yCenter;
    resetMainEntity();
    updateCoordinates(entities);
  }

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param screenHeight screen height of the game used to determine if entities move on or off screen
   * @param screenWidth screen width of the game used to determine if entities move on or off screen
   * sets the initial position off all of the entities based upon the coordinates of the main entity
   */
  public void initialize(EntityList entities, double screenHeight, double screenWidth) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    mainEntity = entities.getMainEntity();
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    setToCenter(entities);
  }


  private void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(xChange, yChange);
  }

  private void resetMainEntity() {
    mainEntity.setX(xCenter);
    mainEntity.setY(yCenter);
  }
}
