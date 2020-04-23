package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CenteredDirectionController extends DirectionController {

  private double xCenter;
  private double yCenter;
  private double myScreenHeight;
  private double myScreenWidth;
  private double xChange;
  private double yChange;

  public CenteredDirectionController() {
  }
  public void updateCameraPosition(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getX() != xCenter | mainEntity.getY() != yCenter){
      setToCenter(entities, mainEntity);
    }
  }

  public void setToCenter(EntityList entities, Entity mainEntity) {
    xCenter = myScreenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    yCenter = myScreenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    xChange = mainEntity.getX() - xCenter;
    yChange = mainEntity.getY() - yCenter;
    System.out.println(yChange);
    resetMainEntity(mainEntity);
    updateCoordinates(entities, mainEntity);
  }


  public void initialize(EntityList entities, double screenHeight, double screenWidth,
      Entity mainEntity) {
    myScreenHeight = screenHeight;
    myScreenWidth = screenWidth;
    setToCenter(entities, mainEntity);
  }


  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(xChange, yChange, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity) {
    mainEntity.setX(xCenter);
    mainEntity.setY(yCenter);
  }
}
