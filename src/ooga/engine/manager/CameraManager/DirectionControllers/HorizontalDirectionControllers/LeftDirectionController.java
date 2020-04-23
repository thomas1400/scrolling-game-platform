package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class LeftDirectionController extends HorizontalDirectionController {


  public LeftDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double xCenter = myScreenWidth/2- mainEntity.getBoundsInLocal().getWidth()/2;
    if (mainEntity.getX() < xCenter) {
      setToCenter(entities, 0);
    }
    else if (mainEntity.getX() > myScreenWidth- mainEntity.getBoundsInLocal().getWidth()){
      mainEntity.setX(myScreenWidth-mainEntity.getBoundsInLocal().getWidth());
    }
    checkIfMarioTouchesTopOrBottomOfScreen();
  }

  @Override
  public int getOffset() {
    return 0;
  }
}