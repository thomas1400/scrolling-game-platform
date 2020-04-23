package ooga.engine.manager.CameraManager.DirectionControllers.HorizontalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class RightDirectionController extends HorizontalDirectionController {

  public RightDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, double myScreenHeight, double myScreenWidth) {
    Entity mainEntity = entities.getMainEntity();
    double xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > xCenter) {
      setToCenter(entities, 0);
      System.out.println("1");
    } else if (mainEntity.getX() <= 0) {
      mainEntity.setX(0);
      System.out.println("2");
    }
    checkIfMarioTouchesTopOrBottomOfScreen();
  }

  @Override
  public int getOffset() {
    return 0;
  }
}
