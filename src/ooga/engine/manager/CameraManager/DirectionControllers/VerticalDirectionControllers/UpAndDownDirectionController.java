package ooga.engine.manager.CameraManager.DirectionControllers.VerticalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class UpAndDownDirectionController extends VerticalDirectionController {

  private static final int OFFSET = 100;

  public UpAndDownDirectionController() {
  }
    public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getY() > yCenter + OFFSET){
      setToCenter(entities, OFFSET);
    }
    else if (mainEntity.getY() < yCenter - OFFSET) {
      setToCenter(entities, - OFFSET);
    }
    checkIfMarioTouchesSidesOfScreen();
  }

  @Override
  public int getOffset() {
    return OFFSET;
  }
}