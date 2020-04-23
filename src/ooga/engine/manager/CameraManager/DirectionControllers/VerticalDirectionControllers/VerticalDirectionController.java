package ooga.engine.manager.CameraManager.DirectionControllers.VerticalDirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

abstract public class VerticalDirectionController extends DirectionController {

  private double yCenter;
  private double change;
  private Entity myMainEntity;
  private double myScreenWidth;
  private double myScreenHeight;

  public void setToCenter(EntityList entities, int offset) {
    myMainEntity = entities.getMainEntity();
    yCenter = myScreenHeight / 2 - myMainEntity.getBoundsInLocal().getHeight() / 2 - offset;
    change = myMainEntity.getY() - yCenter - offset;
    resetMainEntity(offset);
    updateCoordinates(entities);
  }

  private void resetMainEntity(int offset) {
    myMainEntity.setY(yCenter + offset);
  }

  private void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(0, change);
  }

  public void checkIfMarioTouchesSidesOfScreen() {
    if (myMainEntity.getX() > myScreenWidth - myMainEntity.getBoundsInLocal().getWidth()) {
      myMainEntity.setX(myScreenWidth - myMainEntity.getBoundsInLocal().getWidth());
    } else if (myMainEntity.getX() < 0) {
      myMainEntity.setX(0);
    }
  }

  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    myMainEntity = mainEntity;
    if (mainEntity.getY() < 0) {
      setToCenter(entities, 0);
    } else if (mainEntity.getY() > screenWidth) {
      setToCenter(entities, 0);
    }
  }

  abstract public int getOffset();
}
