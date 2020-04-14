package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class RightDirectionController extends DirectionController {
  private double xCenter;

  public RightDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    xCenter = screenWidth/2- mainEntity.getBoundsInLocal().getWidth()/2;
    if (mainEntity.getX() > xCenter) {
      change = mainEntity.getX() - xCenter;
      resetMainEntity(mainEntity);
      entities.changeAllCoordinates(change, 0);
      //determineEntitiesOnScreen(entities);
    }
  }


  public void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(change, 0);
  }

  public void resetMainEntity(Entity mainEntity) {
    mainEntity.setX(xCenter);
  }

}
