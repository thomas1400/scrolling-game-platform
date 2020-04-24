package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Abstract class sets up methods for all DirectionControllers that move in a horizontal direction
 * extends DirectionController as it is a subset of a DirectionController
 * @author Cayla Schuval
 */
abstract public class HorizontalDirectionController extends DirectionController {
  private double xCenter;
  private double change;
  private Entity myMainEntity;
  private double myScreenHeight;
  private double myScreenWidth;

  protected void setToCenter(EntityList entities, int offset){
    xCenter = myScreenWidth / 2 - myMainEntity.getBoundsInLocal().getWidth() / 2;
    change = myMainEntity.getX() - xCenter - offset;
    resetMainEntity(offset);
    updateCoordinates(entities);
  }

  private void resetMainEntity(int offset){
    xCenter = myScreenWidth / 2 - myMainEntity.getBoundsInLocal().getWidth() / 2;
    myMainEntity.setX(xCenter+offset);

  }
  private void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(change, 0);
  }

  protected void checkIfMarioTouchesTopOrBottomOfScreen(){
    if (myMainEntity.getY()<=0){
      myMainEntity.setY(0.1);
    }
    /*if (myMainEntity.getY()>=myScreenHeight){
      System.out.println("reset");
     resetMainEntity(getOffset());
    }*/
    //add touching bottom of screen
  }

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param screenHeight screen height of the game used to determine if entities move on or off screen
   * @param screenWidth screen width of the game used to determine if entities move on or off screen
   * sets the initial position off all of the entities based upon the X coordinate of the main entity
   */
  public void initialize(EntityList entities, double screenHeight, double screenWidth) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    myMainEntity = entities.getMainEntity();
    if(myMainEntity.getX()<0){
      setToCenter(entities, getOffset());
    }
    else if(myMainEntity.getX()>screenWidth){
      setToCenter(entities, getOffset());
    }
  }

  /**
   * @return int value associated with the offset from the center which should cause the screen to shift
   */
  abstract public int getOffset();
}
