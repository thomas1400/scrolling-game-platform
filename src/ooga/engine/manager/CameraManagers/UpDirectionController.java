package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class UpDirectionController extends DirectionController {
  private double yCenter;

  public UpDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    yCenter = screenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
    if (mainEntity.getY() < yCenter) {
      change = mainEntity.getY() - yCenter;
      resetMainEntity(mainEntity);
      entities.changeAllCoordinates(0, change);
      //determineEntitiesOnScreen(entities);
    }
  }

  public void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(0, change);
  }

  public void resetMainEntity(Entity mainEntity) {
    mainEntity.setY(yCenter);
  }
}
