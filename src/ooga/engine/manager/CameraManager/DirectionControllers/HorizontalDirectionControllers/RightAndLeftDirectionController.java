package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Subclass of HorizontalDirectionController
 * specifies movement of entity so that screen is only able to move right and left as the main entity's X position changes
 * @author Cayla Schuval
 */
public class RightAndLeftDirectionController extends HorizontalDirectionController {
  private static final int OFFSET = 100;

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param myScreenHeight screen height of the game used to determine if entities move on or off screen
   * @param myScreenWidth screen width of the game used to determine if entities move on or off screen
   * checks the X position of the main entity and updates all entity positions respectively
   */
  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > xCenter + OFFSET){
      setToCenter(entities, OFFSET);
    }
    else if (mainEntity.getX() < xCenter - OFFSET) {
      setToCenter(entities,  - OFFSET);
    }
    checkIfMarioTouchesTopOrBottomOfScreen();
  }

  /**
   * @return int value associated with the offset from the center which should cause the screen to shift
   */
  public int getOffset() {
    return OFFSET;
  }
}