package ooga.engine.manager.CameraManagers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

abstract public class DirectionController {
  protected Entity mainEntity;
  protected double screenHeight;
  protected double screenWidth;
  protected double change;

  public DirectionController (){
  }


  abstract public void updateCoordinates(EntityList entities) ;

  abstract public void resetMainEntity(Entity mainEntity);

  public abstract void updateCameraPosition(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity);
}
