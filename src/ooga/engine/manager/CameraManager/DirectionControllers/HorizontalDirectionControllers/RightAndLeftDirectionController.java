package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class RightAndLeftDirectionController extends HorizontalDirectionController {
  private static final int OFFSET = 100;

  public RightAndLeftDirectionController() {
  }
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

  @Override
  public int getOffset() {
    return OFFSET;
  }
}