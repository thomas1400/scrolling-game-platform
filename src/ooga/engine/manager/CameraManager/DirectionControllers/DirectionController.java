package ooga.engine.manager.CameraManager.DirectionControllers;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

abstract public class DirectionController {

  public DirectionController (){
  }

  public abstract void updateCameraPosition(EntityList entities, Entity mainEntity);
  public abstract void initialize(EntityList entities, double screenHeight, double screenWidth, Entity mainEntity);
}
