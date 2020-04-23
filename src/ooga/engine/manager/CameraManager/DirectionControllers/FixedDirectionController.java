package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class FixedDirectionController extends DirectionController {

  private double xCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double change;

  public FixedDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    if (mainEntity.getX() < 0) {
      mainEntity.setX(0);
    }
    else if (mainEntity.getX() > myScreenWidth- mainEntity.getBoundsInLocal().getWidth()){
      mainEntity.setX(myScreenWidth-mainEntity.getBoundsInLocal().getWidth());
    }
  }

  public void setToCenter(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    change = mainEntity.getX() - xCenter;
    resetMainEntity(mainEntity);
    updateCoordinates(entities, mainEntity);
  }


  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    if(mainEntity.getX()<0){
      setToCenter(entities, mainEntity);
    }
    else if(mainEntity.getX()>screenWidth){
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
