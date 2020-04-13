package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class UpwardsCameraManager extends CameraManager {
  private double yCenter;

  public UpwardsCameraManager(Entity character, double height, double width) {
    super(character, height, width);
    yCenter = screenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
  }

  @Override
  public void updateCamera(EntityList entities) {
    if (mainEntity.getY() < yCenter) {
      change = mainEntity.getY() - yCenter;
      resetMainEntityToCenter();
      entities.changeAllCoordinates(0, change);
      determineEntitiesOnScreen(entities);
    }
  }

  @Override
  public void updateCoordinates(EntityList entities) {
    entities.changeAllCoordinates(0, change);
  }

  @Override
  public void resetMainEntityToCenter() {
    mainEntity.setY(yCenter);
  }
}
