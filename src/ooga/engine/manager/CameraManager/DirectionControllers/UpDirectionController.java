package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.engine.manager.CameraManager.DirectionControllers.DirectionController;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class UpDirectionController extends DirectionController {
  private double yCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double change;

  public UpDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getY() < yCenter) {
      setToCenter(entities, mainEntity);
      //entities.changeAllCoordinates(0, change);
      //determineEntitiesOnScreen(entities);
    }
  }

  public void initialize(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity){
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    if(mainEntity.getY()>screenHeight){
      setToCenter(entities, mainEntity);
    }
  }

  public void setToCenter(EntityList entities, Entity mainEntity) {
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    change = mainEntity.getY() - yCenter;
    resetMainEntity(mainEntity);
    updateCoordinates(entities, mainEntity);
  }

  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(0, change, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity) {
    mainEntity.setY(yCenter);
  }
}
