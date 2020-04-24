package ooga.engine.manager.CameraManager.DirectionControllers.VerticalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Abstract class sets up methods for all DirectionControllers that move in a vertical direction
 * extends DirectionController as it is a subset of a DirectionController
 * @author Cayla Schuval
 */
abstract public class VerticalDirectionController extends DirectionController {
  private double yCenter;
  private double change;
  private Entity myMainEntity;
  private double myScreenWidth;
  private double myScreenHeight;

  protected void setToCenter(EntityList entities, int offset) {
    myMainEntity = entities.getMainEntity();
    yCenter = myScreenHeight / 2 - myMainEntity.getBoundsInLocal().getHeight() / 2;
    change = myMainEntity.getY() - yCenter - offset;
    //change = myMainEntity.getY();
    resetMainEntity(offset);
    //myMainEntity.setY(0.1);
    updateCoordinates(entities);
  }

  private void resetMainEntity(int offset) {
    yCenter = myScreenHeight / 2 - myMainEntity.getBoundsInLocal().getHeight() / 2;
    myMainEntity.setY(yCenter + offset);
  }

  private void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(0, change);
  }

  protected void checkIfMarioTouchesSidesOfScreen() {
    if (myMainEntity.getX() > myScreenWidth - myMainEntity.getBoundsInLocal().getWidth()) {
      myMainEntity.setX(myScreenWidth - myMainEntity.getBoundsInLocal().getWidth());
    } else if (myMainEntity.getX() < 0) {
      myMainEntity.setX(0);
    }
  }

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param screenHeight screen height of the game used to determine if entities move on or off screen
   * @param screenWidth screen width of the game used to determine if entities move on or off screen
   * sets the initial position off all of the entities based upon the Y coordinate of the main entity
   */
  public void initialize(EntityList entities, double screenHeight, double screenWidth) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    myMainEntity = entities.getMainEntity();
    if (myMainEntity.getY() < 0) {
      setToCenter(entities, 0);
    } else if (myMainEntity.getY() > screenWidth) {
      setToCenter(entities, 0);
    }
  }

  /**
   * @return int value associated with the offset from the center which should cause the screen to shift
   */
  abstract public int getOffset();
}
