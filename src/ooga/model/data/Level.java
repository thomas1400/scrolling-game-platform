package ooga.model.data;

import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Simply a package for all relevant Level data. Generated within the LevelBuilder class and
 * passed to the LevelLoop to retrieve all relevant information.
 *
 * Could theoretically be used by a "LevelSaver" class to turn back into a file
 */
public class Level {

  private String myName;
  private Entity myMainEntity;
  private EntityList myEntities;

  public Level(String name, EntityList entities){
    myName = name;
    myMainEntity = entities.getMainEntity();
    myEntities = entities;
  }

  public String getName(){
    return myName;
  }

  public Entity getMainEntity() {
    return myMainEntity;
  }

  public EntityList getEntities(){
    return myEntities;
  }

}
