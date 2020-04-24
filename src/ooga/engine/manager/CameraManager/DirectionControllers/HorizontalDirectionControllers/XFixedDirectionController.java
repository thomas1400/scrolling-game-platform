package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Subclass of HorizontalDirectionController
 * screen position does not change but is initialized with the main entity's X position
 * @author Cayla Schuval
 */
public class XFixedDirectionController extends HorizontalDirectionController {

  /**
   * @param entities EntityList containing all of the entities in the game whose positions update as the main entity moves
   * @param myScreenHeight screen height of the game used to determine if entities move on or off screen
   * @param myScreenWidth screen width of the game used to determine if entities move on or off screen
   * checks that the mainEntity remains within the bounds of the game
   */
  public void updateCameraPosition(EntityList entities, double myScreenHeight,
      double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    if (mainEntity.getX() < 0) {
      mainEntity.setX(0);
    } else if (mainEntity.getX() > myScreenWidth - mainEntity.getBoundsInLocal().getWidth()) {
      mainEntity.setX(myScreenWidth - mainEntity.getBoundsInLocal().getWidth());
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
