package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class VerticalDirectionController extends DirectionController {

  private double yCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double change;
  private static final int OFFSET = 100;

  public VerticalDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getY() > yCenter + OFFSET){
      setToCenter(entities, mainEntity, OFFSET);
    }
    else if (mainEntity.getY() < yCenter - OFFSET) {
      setToCenter(entities, mainEntity, - OFFSET);
    }
  }

  private void setToCenter(EntityList entities, Entity mainEntity, int offset) {
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    change = mainEntity.getY() - yCenter - offset;
    resetMainEntity(mainEntity, offset);
    updateCoordinates(entities, mainEntity);
  }

  @Override
  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    if(mainEntity.getY()<0){
      setToCenter(entities, mainEntity, 0);
    }
    else if(mainEntity.getY()>screenWidth){
      setToCenter(entities, mainEntity, 0);
    }

  }

  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(0, change, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity, int offset) {
    mainEntity.setY(yCenter+offset);
  }
}