package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Subclass of HorizontalDirectionController
 * specifies movement of entity so that screen is only able to move right as the main entity's X position moves right
 * @author Cayla Schuval
 */
public class RightDirectionController extends HorizontalDirectionController {

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param myScreenHeight screen height of the game used to determine if entities move on or off screen
   * @param myScreenWidth screen width of the game used to determine if entities move on or off screen
   * checks the X position of the main entity and updates all entity positions respectively
   */
  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > xCenter) {
      setToCenter(entities, 0);
    } else if (mainEntity.getX() <= 0) {
      mainEntity.setX(0);
    }
    checkIfMarioTouchesTopOrBottomOfScreen();
  }

  /**
   * @return int value associated with the offset from the center which should cause the screen to shift
   */
  public int getOffset() {
    return 0;
  }
}
