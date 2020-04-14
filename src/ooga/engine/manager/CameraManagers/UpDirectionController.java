package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class UpDirectionController extends DirectionController {
  private double yCenter;

  public UpDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    if (mainEntity.getY() < yCenter) {
      setToCenter(entities, screenHeight, screenWidth, mainEntity);
      //entities.changeAllCoordinates(0, change);
      //determineEntitiesOnScreen(entities);
    }
  }

  @Override
  public void setToCenter(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    yCenter = screenHeight/2- mainEntity.getBoundsInLocal().getHeight()/2;
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
