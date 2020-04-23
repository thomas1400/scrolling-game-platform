package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class XFixedDirectionController extends HorizontalDirectionController {

  public XFixedDirectionController() {
  }

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

  @Override
  public int getOffset() {
    return 0;
  }
}
