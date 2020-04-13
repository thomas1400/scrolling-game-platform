package ooga.engine.manager.CameraManagers;

import ooga.engine.manager.CameraManagers.CameraManager;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class StandardCameraManager extends CameraManager {
  private double xCenter;

  public StandardCameraManager(Entity character, double height, double width) {
    super(character, height, width);
    xCenter = screenWidth/2- mainEntity.getBoundsInLocal().getWidth()/2;
  }

  @Override
  public void updateCamera(EntityList entities) {
    if (mainEntity.getX() > xCenter) {
      change = mainEntity.getX() - xCenter;
      resetMainEntityToCenter();
      entities.changeAllCoordinates(change, 0);
      determineEntitiesOnScreen(entities);
    }
  }

  @Override
  public void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(change, 0);
  }

  @Override
  public void resetMainEntityToCenter() {
    mainEntity.setX(xCenter);
  }
}
//
