package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class RightDirectionController extends DirectionController {

  private double xCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double change;

  public RightDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > xCenter) {
      setToCenter(entities, mainEntity);
    } else if (mainEntity.getX() <= 0) {
      mainEntity.setX(0);
    }
    else if (mainEntity.getY()<=0){
      mainEntity.setY(0.1);
    }
  }

  public void setToCenter(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    change = mainEntity.getX() - xCenter;
    resetMainEntity(mainEntity);
    updateCoordinates(entities, mainEntity);
  }

  @Override
  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    if(mainEntity.getX()<0){
      setToCenter(entities, mainEntity);
    }

  }


  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(change, 0, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity) {
    mainEntity.setX(xCenter);
  }
}
