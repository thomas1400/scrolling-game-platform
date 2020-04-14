package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class RightDirectionController extends DirectionController {
  private double xCenter;

  public RightDirectionController() {
  }

  public void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    if (mainEntity.getX() > xCenter) {
      setToCenter(entities, screenHeight, screenWidth, mainEntity);
      //entities.changeAllCoordinates(change, 0);
      //determineEntitiesOnScreen(entities);
    }
    else if (mainEntity.getX()<0){
      mainEntity.setX(0);
    }
  }

  public void setToCenter(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity) {
    xCenter = screenWidth / 2 - mainEntity.getBoundsInLocal().getWidth() / 2;
    if (mainEntity.getX() > screenWidth) {
      change = mainEntity.getX() - xCenter;
      resetMainEntity(mainEntity);
      updateCoordinates(entities, mainEntity);
    }
    else {
      change = mainEntity.getX() - xCenter;
      resetMainEntity(mainEntity);
      updateCoordinates(entities, mainEntity);
    }
  }


  private void updateCoordinates(EntityList entities, Entity mainEntity) {
    entities.changeAllCoordinates(change, 0, mainEntity);
  }

  private void resetMainEntity(Entity mainEntity) {
    mainEntity.setX(xCenter);
  }

}
