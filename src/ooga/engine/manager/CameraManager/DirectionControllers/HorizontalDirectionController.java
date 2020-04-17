package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class HorizontalDirectionController extends DirectionController {

  private double xCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double change;
  private static final int OFFSET = 100;

  public HorizontalDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > xCenter + OFFSET){
      setToCenter(entities, mainEntity, OFFSET);
    }
    else if (mainEntity.getX() < xCenter - OFFSET) {
      setToCenter(entities, mainEntity, - OFFSET);
    }
    if (mainEntity.getY()<=0){
      mainEntity.setY(0.1);
    }
  }

  private void setToCenter(EntityList entities, Entity mainEntity, int offset) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    change = mainEntity.getX() - xCenter - offset;
    resetMainEntity(mainEntity, offset);
    updateCoordinates(entities, mainEntity);
  }

  @Override
  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    if(mainEntity.getX()<0){
      setToCenter(entities, mainEntity, 0);
    }
    else if(mainEntity.getX()>screenWidth){
      setToCenter(entities, mainEntity, 0);
    }

  }

  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(change, 0, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity, int offset) {
    mainEntity.setX(xCenter+offset);
  }
}