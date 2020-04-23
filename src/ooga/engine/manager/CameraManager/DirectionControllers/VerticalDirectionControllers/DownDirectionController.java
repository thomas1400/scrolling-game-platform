package ooga.engine.manager.CameraManager.DirectionControllers.VerticalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class DownDirectionController extends VerticalDirectionController {

  public DownDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double yCenter = myScreenHeight / 2 - mainEntity.getBoundsInLocal().getHeight() / 2;
    if (mainEntity.getY() > yCenter) {
      setToCenter(entities, 0);
    }
    checkIfMarioTouchesSidesOfScreen();
  }


  @Override
  public int getOffset() {
    return 0;
  }
}
